package viiksiniekka

import viiksiniekka.StringUtils.{indent, ordinaryTextToCamelCase, ordinaryTextToConstantCase, upcaseFirst, downcaseFirst}

class JavaDataGenerator extends JavaGenerator {
  override def generate(domain: Domain): Map[String, String] = {
    val elems: Seq[(String, String)] =
      domain.
        getDomainTypes.
        flatMap(domainTypeToSeqOfSourceFileEntries) ++
      domain
        .getAggregates.flatMap { a: Aggregate =>
          a.getComponents.flatMap { d: DataContainer =>
            List((sourceFileName(d.getName, d.getPackage.getDataPackage), dataContainerSource(d)))
          }
        }
    Map(elems: _*)
  }

  private def domainTypeToSeqOfSourceFileEntries(domainType: DomainType): Seq[(String, String)] = {
    domainType match {
      case e: Entity => Seq((sourceFileName(e.getName, e.getPackage.getDataPackage), dataContainerSource(e)))
      case v: ValueObject => Seq((sourceFileName(v.getName, v.getPackage.getDataPackage), dataContainerSource(v)))
      case e: Enumeration => Seq(
        (sourceFileName(e.getName, e.getPackage.getDataPackage),
          enumerationSource(e)),
        (sourceFileName(e.getName + "Visitor", e.getPackage.getVisitorPackage),
          enumerationVisitorSource(e)))

    }
  }

  private def sourceFileName(name: String, package_ : Package): String = {
    package_ match {
      case OrdinaryPackage(packageName) => ProjectStructure.SrcMainJava + "/" + packageName.replace('.', '/') + '/' + name + ".java"
      case PredefPackage => ProjectStructure.SrcMainJava + "/" + name + ".java"
    }
  }

  private def dataContainerSource(e: DataContainer): String = {
    s"""${packageDeclaration(e.getPackage.getDataPackage)}
       |${imports(e)}
       |
       |/**
       | *${
      if (e.getDocumentation.isEmpty) {
        ""
      } else {
        s" ${e.getDocumentation}"
      }
    }
       | */
       |public class ${e.getName} ${
      e.getExtends match {
        case None => ""
        case Some(superType) => s"extends ${superType.getName} "
      }
    }{
       |${e.getDeclaredFields.map(fieldDeclaration).mkString("\n")}
       |
       |    public ${e.getName}(
       |${e.getFields.map(constructorParameterDeclaration).map("            " + _).mkString(",\n")}) {${
      e.getExtends match {
        case None => ""
        case Some(superType) => s"\n        super(${
          superType match {
            case se: DataContainer => se.getFields.map(_.getName).mkString(", ")
          }
        });"
      }
    }
       |${e.getDeclaredFields.map(requireNonNull(e)).map("        " + _).mkString("\n")}
       |${e.getDeclaredFields.map(constructorParameterAssignment).map("        " + _ + ';').mkString("\n")}
       |    }
       |${e.getDeclaredFields.map(getter).map(indent(4)).map("\n" + _).mkString("\n")}
       |
       |    @Override
       |    public String toString() {
       |        return "${e.getName}{"\n                + ${e.getFields.map(f => "\"" + f.getName + "='\" + get" + upcaseFirst(f.getName) + "() + \"'\"").mkString("\n                + \", \" + ")}
       |                + "}";
       |    }
       |
       |    @Override
       |    public boolean equals(Object o) {
       |        if (this == o) return true;
       |        if (o == null || getClass() != o.getClass()) return false;
       |
       |        ${e.getName} other = (${e.getName}) o;
       |
       |${e.getFields.map(f => indent(8)(s"if (!get${upcaseFirst(f.getName)}().equals(other.get${upcaseFirst(f.getName)}())) return false;")).mkString("\n")}
       |        return true;
       |    }
       |
       |    @Override
       |    public int hashCode() {
       |        int result = 0;
       |${e.getFields.map(f => indent(8)("result = 31 * result + get" + upcaseFirst(f.getName) + "().hashCode();")).mkString("\n")}
       |        return result;
       |    }
       |}
       |""".stripMargin
  }

  private def classJavaDoc(d: DomainType): String = {
    if (d.getDocumentation.isEmpty) {
      ""
    } else {
      s"""
         |/**
         | * ${d.getDocumentation}
         | */
         |""".stripMargin
    }
  }

  private def enumerationSource(e: Enumeration): String = {
    s"""${packageDeclaration(e.getPackage.getDataPackage)}${classJavaDoc(e)}public enum ${e.getName} {
       |    ${e.getMembers.map(m => s"""${ordinaryTextToConstantCase(m)}("${m}")""").mkString(", ")};
       |
       |    private final String value;
       |
       |    private ${e.getName}(String value) {
       |        this.value = value;
       |    }
       |
       |    public String getValue() {
       |        return value;
       |    }
       |
       |    public static ${e.getName} forValue(String input) {
       |        switch (input) {
       |${e.getMembers.map(m => s"""            case "${m}":\n                return ${ordinaryTextToConstantCase(m)};""").mkString("\n")}
       |            default:
       |                throw new IllegalArgumentException("No ${e.getName} value for input '" + input + "'.");
       |        }
       |    }
       |}
       |""".stripMargin
  }

  private def enumerationVisitorSource(e: Enumeration): String = {
    s"""${packageDeclaration(e.getPackage.getVisitorPackage)}
       |import ${e.getPackage.getDataPackage.name}.${e.getName};
       |
       |public interface ${e.getName}Visitor<T> {
       |    ${e.getMembers.map(m => s"""T visit${upcaseFirst(ordinaryTextToCamelCase(m))}(${e.getName} ${downcaseFirst(e.getName)});""").mkString("\n\n    ")}
       |}
       |""".stripMargin
  }

  private def typeSimpleName(t: Type, optional: Boolean): String = {
    val basicPart = t match {
      case IntegerType => "Integer"
      case LongType => "Long"
      case StringType => "String"
      case BooleanType => "Boolean"
      case LocalTimeType => "LocalTime"
      case LocalDateTimeType => "LocalDateTime"
      case d: DomainType => d.getName
      case e: ExternalType => e.getName
    }
    if (optional) {
      s"Optional<${basicPart}>"
    } else {
      basicPart
    }
  }

  private def fieldDeclaration(f: Field): String = f match {
    case OrdinaryField(name, _, optional, type_) => "    private final " + typeSimpleName(type_, optional) + ' ' + name + ';'
    case ListField(name, _, type_) => s"    private final List<${typeSimpleName(type_, false)}> ${name};"
  }

  private def constructorParameterDeclaration(f: Field): String = f match {
    case OrdinaryField(name, _, optional, type_) => typeSimpleName(type_, optional) + ' ' + name
    case ListField(name, _, type_) => s"List<${typeSimpleName(type_, false)}> " + name
  }

  private def requireNonNull(e: DataContainer)(f: Field): String = s"""Objects.requireNonNull(${f.getName}, "Field '${f.getName}' of class ${e.getName} cannot be null.");"""

  private def constructorParameterAssignment(f: Field): String = f match {
    case of: OrdinaryField => s"this.${of.getName} = ${of.getName}"
    case lf: ListField => s"this.${lf.getName} = Collections.unmodifiableList(${lf.getName})"
  }

  private def getter(f: Field): String =
    f match {
      case OrdinaryField(name, documentation, optional, type_) =>
        s"""/**
           | *${
          if (documentation.isEmpty) {
            ""
          } else {
            s" ${documentation}"
          }
        }
           | */
           |public ${typeSimpleName(type_, optional)} get${upcaseFirst(name)}() {
           |    return ${f.getName};
           |}""".stripMargin
      case ListField(name, documentation, type_) =>
        s"""/**
           | *${
          if (documentation.isEmpty) {
            ""
          } else {
            s" ${documentation}"
          }
        }
           | */
           |public List<${typeSimpleName(type_, false)}> get${upcaseFirst(name)}() {
           |    return ${f.getName};
           |}""".stripMargin
    }

  private def imports(e: DataContainer): String = {
    var importedClasses: Seq[String] = Seq("java.util.Objects")
    if (hasOptionals(e)) {
      importedClasses :+= "java.util.Optional"
    }
    importedClasses = importedClasses ++ importsFromFields(e.getFields)
    importedClasses.sorted.distinct.map(s => s"import ${s};").mkString("\n")
  }

  private def importsFromFields(fields: Seq[Field]): Seq[String] = {
    fields.map(_.getType).flatMap(importFromType) ++ (if (fields.exists {
      case _: ListField => true
      case _ => false
    }) {
      Seq("java.util.Collections", "java.util.List")
    } else {
      Seq()
    })
  }

  private def importFromType(t: Type): Option[String] = {
    t match {
      case LocalTimeType => Some("java.time.LocalTime")
      case LocalDateTimeType => Some("java.time.LocalDateTime")
      case _ => None
    }
  }

  private def hasOptionals(e: DataContainer): Boolean = {
    e.getFields.exists {
      case OrdinaryField(_, _, true, _) => true
      case _ => false
    }
  }
}

package boilerplatr

import boilerplatr.StringUtils.{downcaseFirst, indent, upcaseFirst}

class JavaBuilderGenerator extends JavaGenerator {
  override def generate(domain: Domain): Map[String, String] = {
    val elems = domain.getDomainTypes.flatMap {
      case e@Entity(name, _, package_, _, _, _) => Some(sourceFileName(s"${name}Builder", package_.getBuilderPackage), dataContainerSource(e))
      case v@ValueObject(name, _, package_, _, _, _) => Some(sourceFileName(s"${name}Builder", package_.getBuilderPackage), dataContainerSource(v))
      case _: Enumeration => None
    } ++ domain.getAggregates.flatMap { a: Aggregate =>
      a.getComponents.map { d: DataContainer =>
        (sourceFileName(s"${d.getName}Builder", d.getPackage.getBuilderPackage), dataContainerSource(d))
      }
    }
    Map(elems: _*)
  }

  private def sourceFileName(name: String, package_ : Package): String = {
    package_ match {
      case OrdinaryPackage(packageName) => ProjectStructure.SrcMainJava + "/" + packageName.replace('.', '/') + '/' + name + ".java"
      case PredefPackage => ProjectStructure.SrcMainJava + "/" + name + ".java"
    }
  }

  private def dataContainerSource(e: DataContainer): String = {
    s"""${packageDeclaration(e.getPackage.getBuilderPackage)}
       |${imports(e)}
       |
       |public class ${e.getName}Builder {
       |${e.getFields.map(fieldDeclaration).mkString("\n")}
       |
       |    public static ${e.getName}Builder from(${e.getName} ${downcaseFirst(e.getName)}) {
       |        return new ${e.getName}Builder()
       |${
      e.getFields.map {
        case f@(o: OrdinaryField) if o.isOptional => s".with${upcaseFirst(f.getName)}Opt(${downcaseFirst(e.getName)}.get${upcaseFirst(f.getName)}())"
        case f@(o: OrdinaryField) if !o.isOptional => s".with${upcaseFirst(f.getName)}(${downcaseFirst(e.getName)}.get${upcaseFirst(f.getName)}())"
        case f@(l: ListField) => s".with${upcaseFirst(f.getName)}(${downcaseFirst(e.getName)}.get${upcaseFirst(f.getName)}().stream().map(${f.getType.getName}Builder::from).collect(Collectors.toList()))"
      }.map(indent(16)).mkString("\n")
    };
       |    }
       |${e.getFields.map(getterSetters(e)).map(indent(4)).map("\n" + _).mkString("\n")}
       |
       |    public ${e.getName} build() {
       |        return new ${e.getName}(${
      e.getFields.map {
        case f@(of: OrdinaryField) =>
          f.getName + {
            if (of.isOptional) {
              f.getType match {
                case e: DataContainer => s".map(${e.getName}Builder::build)"
                case _ => ""
              }
            } else {
              f.getType match {
                case _: DataContainer => ".build()"
                case _ => ""
              }
            }
          }
        case lf: ListField => s"${lf.getName}.stream().map(${lf.getType.getName}Builder::build).collect(Collectors.toList())"
      }.mkString(", ")
    });
    }
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
    case OrdinaryField(name, _, optional, type_) => type_ match {
      case _: Entity => if (optional) {
        "    private Optional<" + typeSimpleName(type_, false) + "Builder> " + name + " = Optional.empty();"
      } else {
        "    private " + typeSimpleName(type_, false) + "Builder " + name + " = new " + typeSimpleName(type_, optional) + "Builder();"
      }
      case _: ValueObject => if (optional) {
        "    private Optional<" + typeSimpleName(type_, false) + "Builder> " + name + " = Optional.empty();"
      } else {
        "    private " + typeSimpleName(type_, false) + "Builder " + name + " = new " + typeSimpleName(type_, optional) + "Builder();"
      }
      case _ => if (optional) {
        "    private " + typeSimpleName(type_, true) + ' ' + name + " = Optional.empty();"
      } else {
        "    private " + typeSimpleName(type_, false) + ' ' + name + ";"
      }
    }
    case ListField(name, documentation, type_) => s"    private List<${typeSimpleName(type_, false)}Builder> ${name} = new ArrayList<>();"
  }

  private def javadocForField(f: Field): String =
    s"""/**
       | *${
      if (f.getDocumentation.isEmpty) {
        ""
      } else {
        s" ${f.getDocumentation}"
      }
    }
       | */""".stripMargin


  private def getterSetters(e: DataContainer)(f: Field): String =
    f match {
      case of@OrdinaryField(_, _, optional, type_) =>
        type_ match {
          case Entity(_, _, _, _, _, _) => if (optional) {
            getterSettersForOptionalBuilderField(e)(of)
          } else {
            getterSettersForBuilderField(e)(of)
          }
          case ValueObject(_, _, _, _, _, _) => if (optional) {
            getterSettersForOptionalBuilderField(e)(of)
          } else {
            getterSettersForBuilderField(e)(of)
          }
          case _ => if (optional) {
            getterSettersForOptionalValueField(e)(of)
          } else {
            getterSettersForValueField(e)(of)
          }
        }
      case lf@ListField(name, documentation, type_) =>
        getterSettersForListField(e)(lf)
    }

  private def getterSettersForValueField(e: DataContainer)(f: OrdinaryField) =
    s"""${javadocForField(f)}
       |public ${e.getName}Builder with${upcaseFirst(f.getName)}(${typeSimpleName(f.getType, f.isOptional)} ${f.getName}) {
       |    Objects.requireNonNull(${f.getName});
       |    this.${f.getName} = ${f.getName};
       |    return this;
       |}""".stripMargin

  private def getterSettersForOptionalValueField(e: DataContainer)(f: OrdinaryField) =
    s"""${javadocForField(f)}
       |public ${e.getName}Builder with${upcaseFirst(f.getName)}(${typeSimpleName(f.getType, false)} ${f.getName}) {
       |    Objects.requireNonNull(${f.getName});
       |    this.${f.getName} = Optional.of(${f.getName});
       |    return this;
       |}
       |
       |${javadocForField(f)}
       |private ${e.getName}Builder with${upcaseFirst(f.getName)}Opt(${typeSimpleName(f.getType, true)} ${f.getName}) {
       |    Objects.requireNonNull(${f.getName});
       |    this.${f.getName} = ${f.getName};
       |    return this;
       |}
       |
       |${javadocForField(f)}
       |public ${e.getName}Builder without${upcaseFirst(f.getName)}() {
       |    this.${f.getName} = Optional.empty();
       |    return this;
       |}""".stripMargin

  private def getterSettersForBuilderField(e: DataContainer)(f: OrdinaryField) =
    s"""${javadocForField(f)}
       |public ${typeSimpleName(f.getType, false)}Builder get${upcaseFirst(f.getName)}() {
       |    return ${f.getName};
       |}
       |
       |${javadocForField(f)}
       |public ${e.getName}Builder with${upcaseFirst(f.getName)}(${typeSimpleName(f.getType, false)}Builder ${f.getName}) {
       |    Objects.requireNonNull(${f.getName});
       |    this.${f.getName} = ${f.getName};
       |    return this;
       |}
       |
       |${javadocForField(f)}
       |public ${e.getName}Builder with${upcaseFirst(f.getName)}(${typeSimpleName(f.getType, false)} ${f.getName}) {
       |    Objects.requireNonNull(${f.getName});
       |    this.${f.getName} = ${typeSimpleName(f.getType, false)}Builder.from(${f.getName});
       |    return this;
       |}""".stripMargin

  private def getterSettersForOptionalBuilderField(e: DataContainer)(f: OrdinaryField) =
    s"""${javadocForField(f)}
       |public Optional<${typeSimpleName(f.getType, false)}Builder> get${upcaseFirst(f.getName)}() {
       |    return ${f.getName};
       |}
       |
       |${javadocForField(f)}
       |public ${e.getName}Builder with${upcaseFirst(f.getName)}(${typeSimpleName(f.getType, false)}Builder ${f.getName}) {
       |    Objects.requireNonNull(${f.getName});
       |    this.${f.getName} = Optional.of(${f.getName});
       |    return this;
       |}
       |
       |${javadocForField(f)}
       |public ${e.getName}Builder with${upcaseFirst(f.getName)}(${typeSimpleName(f.getType, false)} ${f.getName}) {
       |    Objects.requireNonNull(${f.getName});
       |    this.${f.getName} = Optional.of(${typeSimpleName(f.getType, false)}Builder.from(${f.getName}));
       |    return this;
       |}
       |
       |${javadocForField(f)}
       |private ${e.getName}Builder with${upcaseFirst(f.getName)}Opt(${typeSimpleName(f.getType, true)} ${f.getName}) {
       |    Objects.requireNonNull(${f.getName});
       |    this.${f.getName} = ${f.getName}.map(${typeSimpleName(f.getType, false)}Builder::from);
       |    return this;
       |}
       |
       |${javadocForField(f)}
       |public ${e.getName}Builder without${upcaseFirst(f.getName)}() {
       |    this.${f.getName} = Optional.empty();
       |    return this;
       |}""".stripMargin

  private def getterSettersForListField(e: DataContainer)(f: ListField) =
    s"""${javadocForField(f)}
       |public List<${typeSimpleName(f.getType, false)}Builder> get${upcaseFirst(f.getName)}() {
       |    return ${f.getName};
       |}
       |
       |${javadocForField(f)}
       |public ${e.getName}Builder with${upcaseFirst(f.getName)}(List<${typeSimpleName(f.getType, false)}Builder> ${f.getName}) {
       |    Objects.requireNonNull(${f.getName});
       |    this.${f.getName} = ${f.getName};
       |    return this;
       |}""".stripMargin

  private def imports(e: DataContainer): String = {
    var importedClasses: Seq[String] = Seq("java.util.Objects")
    if (hasOptionals(e)) {
      importedClasses :+= "java.util.Optional"
    }
    importedClasses :+= s"${e.getPackage.getDataPackage.name}.${e.getName}"
    importedClasses = importedClasses ++ importsFromFields(e.getFields)

    renderImports(importedClasses)
  }

  private def importsFromFields(fields: Seq[Field]): Seq[String] = {
    fields.flatMap {
      case lf: ListField => Seq(
        "java.util.List",
        "java.util.ArrayList",
        "java.util.stream.Collectors"
      )
      case of: OrdinaryField => importsFromType(of.getType)
    }
  }

  private def importsFromType(t: Type): Seq[String] = {
    t match {
      case d: DomainType => Seq(
        s"${d.getPackage.getDataPackage.name}.${d.getName}"
      )
      case LocalTimeType => Seq("java.time.LocalTime")
      case LocalDateTimeType => Seq("java.time.LocalDateTime")
      case _ => Seq()
    }
  }

  private def hasOptionals(e: DataContainer): Boolean = {
    e.getFields.exists {
      case OrdinaryField(_, _, true, _) => true
      case _ => false
    }
  }
}

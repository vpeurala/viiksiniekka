package boilerplatr

import boilerplatr.StringUtils.{indent, ordinaryTextToCamelCase, ordinaryTextToConstantCase, upcaseFirst}

class JavaExamplesGenerator extends JavaGenerator {
  override def generate(domain: Domain): Map[String, String] = {
    val elems = domain.getDomainTypes.flatMap {
      case e@Entity(name, _, package_, _, _, _) => Some(sourceFileName(s"${name}Examples", package_.getExamplePackage), entityOrValueObjectSource(e))
      case v@ValueObject(name, _, package_, _, _, _) => Some(sourceFileName(s"${name}Examples", package_.getExamplePackage), entityOrValueObjectSource(v))
      case _: Enumeration => None
    }
    Map(elems: _*)
  }

  private def sourceFileName(name: String, package_ : Package): String = {
    package_ match {
      case OrdinaryPackage(packageName) => ProjectStructure.SrcTestJava + "/" + packageName.replace('.', '/') + '/' + name + ".java"
      case PredefPackage => ProjectStructure.SrcMainJava + "/" + name + ".java"
    }
  }

  private def entityOrValueObjectSource(e: DataContainer): String = {
    s"""${packageDeclaration(e.getPackage.getExamplePackage)}${imports(e)}
       |public class ${e.getName}Examples {
       |${e.getExamples.map(example(e)).map(indent(4)).mkString("\n\n")}
       |}
       |""".stripMargin
  }

  private def example(o: DataContainer)(e: Example): String = {
    s"""public static ${typeSimpleName(o, false)}Builder ${ordinaryTextToCamelCase(e.name)}() {
       |    return new ${typeSimpleName(o, false)}Builder()
       |${e.fieldValues.map(fv => s""".with${upcaseFirst(fv.getField.getName)}(${displayValue(fv)})""").map(indent(12)).mkString("\n")};
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

  private def displayValue(fieldValue: FieldValue): String = {
    def basicDisplayValue(type_ : Type): String = {
      type_ match {
        case StringType => fieldValue match {
          case _: ReferenceFieldValue => throw new IllegalArgumentException(s"Cannot have a reference value on a String field: '${fieldValue}'.")
          case SimpleFieldValue(_, value) => "\"" + value + "\""
          case ListFieldValue(_, _) => throw new Error("We should never arrive here. This is a bug.")
        }
        case d: DataContainer => fieldValue match {
          case _: SimpleFieldValue => throw new IllegalArgumentException(s"Cannot have a simple value on a domain type field: '${fieldValue}'.")
          case ReferenceFieldValue(field, ref) => {
            val exampleOpt = d.getExamples.find(_.name == ref)
            exampleOpt match {
              case None => throw new IllegalArgumentException(s"Reference not found: '${ref}' with field type: '${field.getType.getName}' for fieldValue: '${fieldValue}'.")
              case Some(example: Example) => s"${d.getName}Examples.${ordinaryTextToCamelCase(example.name)}()"
            }
          }
          case ListFieldValue(_, _) => throw new Error("We should never arrive here. This is a bug.")
        }
        case e: Enumeration => fieldValue match {
          case s: SimpleFieldValue => s"${e.getName}.${ordinaryTextToConstantCase(s.value)}"
          case r: ReferenceFieldValue => "" // TODO what here?
          case ListFieldValue(_, _) => throw new Error("We should never arrive here. This is a bug.")
        }
        case LocalTimeType => fieldValue match {
          case _: ReferenceFieldValue => throw new IllegalArgumentException(s"Cannot have a reference value on a LocalTime field: '${fieldValue}'.")
          case SimpleFieldValue(_, value) => {
            if (!value.matches("\\d{2}:\\d{2}")) {
              throw new IllegalArgumentException(s"Illegal value '${value}' for LocalTime field: the required pattern is HH:mm.")
            }
            val parts = value.split(':')
            val hour = parts(0).toInt
            val minute = parts(1).toInt
            s"LocalTime.of(${hour}, ${minute})"
          }
          case ListFieldValue(_, _) => throw new Error("We should never arrive here. This is a bug.")
        }
        case LocalDateTimeType => fieldValue match {
          case _: ReferenceFieldValue => throw new IllegalArgumentException(s"Cannot have a reference value on a LocalDateTime field: '${fieldValue}'.")
          case SimpleFieldValue(_, value) => {
            if (!value.matches("\\d{2}.\\d{2}.\\d{4} \\d{2}:\\d{2}")) {
              throw new IllegalArgumentException(s"Illegal value '${value}' for LocalTime field: the required pattern is HH:mm.")
            }
            val parts = value.split("[\\.: ]")
            val day = parts(0).toInt
            val month = parts(1).toInt
            val year = parts(2).toInt
            val hour = parts(3).toInt
            val minute = parts(4).toInt
            s"LocalDateTime.of(${year}, ${month}, ${day}, ${hour}, ${minute})"
          }
          case ListFieldValue(_, _) => throw new Error("We should never arrive here. This is a bug.")
        }
        case _ => fieldValue match {
          case _: ReferenceFieldValue => throw new IllegalArgumentException(s"Cannot have a reference value on a type '${type_.getName}' field: '${fieldValue}'.")
          case SimpleFieldValue(_, value) => value
          case ListFieldValue(_, _) => throw new Error("We should never arrive here. This is a bug.")
        }
      }
    }

    fieldValue.getField match {
      case OrdinaryField(_, _, _, type_) => basicDisplayValue(type_)
      case ListField(_, _, type_) => s"""asList(\n${
        type_ match {
          case h: HasExamples => h.getExamples.map({
            e: Example => indent(8)(s"${type_.getName}Examples." + StringUtils.ordinaryTextToCamelCase(e.name) + "()")
          }).mkString(",\n")
          case _ => throw new Error("Not implemented yet.") // TODO implement
        }
      })"""
    }
  }

  private def imports(e: DataContainer): String = {
    var importedClasses: Seq[String] = Seq()
    importedClasses :+= s"${e.getPackage.getBuilderPackage.name}.${e.getName}Builder"
    importedClasses = importedClasses ++ importsFromFields(e.getFields)
    s"\n${renderImports(importedClasses)}\n"
  }

  private def importsFromFields(fields: Seq[Field]): Seq[String] = {
    fields.map(_.getType).flatMap(importsFromType) ++ fields.flatMap {
      case lf: ListField => Seq(
        "static java.util.Arrays.asList"
      ) ++
        importsFromType(lf.getType)
      case of: OrdinaryField => importsFromType(of.getType)
    }
  }

  private def importsFromType(t: Type): Seq[String] = {
    t match {
      case e: Enumeration => Seq(
        s"${e.getPackage.getDataPackage.name}.${e.getName}"
      )
      case LocalTimeType => Seq("java.time.LocalTime")
      case LocalDateTimeType => Seq("java.time.LocalDateTime")
      case _ => Seq()
    }
  }
}

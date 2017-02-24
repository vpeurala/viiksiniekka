package boilerplatr

import scala.language.implicitConversions

class ElmDataGenerator extends ElmGenerator {
  override def generate(domain: Domain): Map[String, String] = {
    Map("src/main/elm/Domain.elm" -> (generateHeader(domain) + (domain.getDomainTypes ++ domain.getAggregates.flatMap(_.getComponents)).sortBy(_.getName).map(generateSingleType).mkString("\n\n\n") + "\n"))
  }

  def generateHeader(domain: Domain): String = {
    "module Domain exposing (..)\n\n" + "{-| Domain module.\n" + "@docs " + (domain.getDomainTypes ++ domain.getAggregates.flatMap(_.getComponents)).map(dt => dt.getName).sorted.mkString(", ") + "\n-}\n\n" + generateImports(domain)
  }

  def generateImports(domain: Domain): String = {
    val p: Seq[String] = domain.getDomainTypes.flatMap { dt => dt.getFields }.flatMap {
      _.getType match {
        case LocalDateTimeType => Some("Date")
        case LocalTimeType => Some("Time")
        case _ => None
      }
    }
    p.distinct.sorted.map { i => s"import ${i}" }.mkString("\n") + "\n\n\n"
  }

  def generateSingleType(dt: DomainType): String = {
    dt match {
      case d: DataContainer =>
        s"""{-| ${if (d.getDocumentation.isEmpty) "-}" else d.getDocumentation + "\n-}"}
           |type alias ${d.getName} =
           |    { ${d.getElmFields.map(ef => ef.name + " : " + ef.elmType).mkString("\n    , ")}
           |    }""".stripMargin
      case e: Enumeration =>
        s"""{-| ${if (e.getDocumentation.isEmpty) "-}" else e.getDocumentation + "\n-}"}
           |type ${e.getName}
           |    = ${e.getMembers.map { m => StringUtils.upcaseFirst(StringUtils.ordinaryTextToCamelCase(m)) }.mkString("\n|    | ")}""".stripMargin
    }
  }
}

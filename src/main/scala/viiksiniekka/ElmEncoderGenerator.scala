package viiksiniekka

import scala.language.implicitConversions

class ElmEncoderGenerator extends ElmGenerator {
  override def generate(domain: Domain): Map[String, String] = {
    Map("src/main/elm/Encoders.elm" -> (generateHeader(domain) + (domain.getDomainTypes ++ domain.getAggregates.flatMap(_.getComponents)).sortBy(_.getName).map(generateSingleType).mkString("\n\n\n") + "\n"))
  }

  def generateHeader(domain: Domain): String = {
    s"""module Encoders
       |    exposing
       |        ( ${(domain.getDomainTypes ++ domain.getAggregates.flatMap(_.getComponents)).map(dt => StringUtils.downcaseFirst(dt.getName)).sorted.mkString("\n        , ")}
       |        )\n\n""".stripMargin + "{-| Encoders module.\n" + "@docs " + (domain.getDomainTypes ++ domain.getAggregates.flatMap(_.getComponents)).map(dt => StringUtils.downcaseFirst(dt.getName)).sorted.mkString(", ") + "\n-}\n\n" + generateImports(domain) + generateBuiltinTypeDecoders(domain)
  }

  def generateImports(domain: Domain): String = {
    val p: Seq[String] = domain.getDomainTypes.flatMap { dt => dt.getFields }.flatMap {
      _.getType match {
        case LocalDateTimeType => Some("Date")
        case LocalTimeType => Some("Time")
        case _ => None
      }
    }
    (p.distinct.sorted.map { i => s"import ${i}" } ++ Seq("import Domain exposing (..)", "import Json.Encode exposing (..)")).mkString("\n") + "\n\n\n"
  }

  def generateBuiltinTypeDecoders(domain: Domain): String = {
    val p: Seq[String] = domain.getDomainTypes.flatMap { dt => dt.getFields }.flatMap {
      _.getType match {
        case LocalDateTimeType => Some(
          s"""date : Date.Date -> Value
             |date o =
             |    string (toString o)
             |""".stripMargin)
        case LocalTimeType => Some("Time")
        case _ => None
      }
    }
    val maybe: String =
      s"""maybe : (a -> Value) -> Maybe a -> Value
         |maybe enc o =
         |    case o of
         |        Nothing ->
         |            null
         |
         |        Just x ->
         |            enc x""".stripMargin
    (p.distinct ++ Seq(maybe)).sorted.mkString("\n\n") + "\n\n\n"
  }

  def generateSingleType(dt: DomainType): String = {
    dt match {
      case d: DataContainer =>
        s"""{-| -}
           |${StringUtils.downcaseFirst(d.getName)} : ${d.getName} -> Value
           |${StringUtils.downcaseFirst(d.getName)} o =
           |    object
           |        [ ${d.getElmFields.map(f => s"""( "${f.name}", ${f.encoder} )""").mkString("\n        , ")}
           |        ]""".stripMargin
      case e: Enumeration =>
        s"""{-| -}
           |${StringUtils.downcaseFirst(e.getName)} : ${e.getName} -> Value
           |${StringUtils.downcaseFirst(e.getName)} o =
           |    string (toString o)""".stripMargin
    }
  }
}

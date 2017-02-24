package viiksiniekka

import scala.language.implicitConversions

class ElmDecoderGenerator extends ElmGenerator {
  override def generate(domain: Domain): Map[String, String] = {
    Map("src/main/elm/Decoders.elm" -> (generateHeader(domain) + (domain.getDomainTypes ++ domain.getAggregates.flatMap(_.getComponents)).sortBy(_.getName).map(generateSingleType).mkString("\n\n\n") + "\n"))
  }

  def generateHeader(domain: Domain): String = {
    s"""module Decoders
       |    exposing
       |        ( ${(domain.getDomainTypes ++ domain.getAggregates.flatMap(_.getComponents)).map(dt => StringUtils.downcaseFirst(dt.getName)).sorted.mkString("\n        , ")}
       |        )\n\n""".stripMargin + "{-| Decoders module.\n" + "@docs " + (domain.getDomainTypes ++ domain.getAggregates.flatMap(_.getComponents)).map(dt => StringUtils.downcaseFirst(dt.getName)).sorted.mkString(", ") + "\n-}\n\n" + generateImports(domain) + generateBuiltinTypeDecoders(domain)
  }

  def generateImports(domain: Domain): String = {
    val p: Seq[String] = domain.getDomainTypes.flatMap { dt => dt.getFields }.flatMap {
      _.getType match {
        case LocalDateTimeType => Some("Date")
        case LocalTimeType => Some("Time")
        case _ => None
      }
    }
    (p.distinct.sorted.map { i => s"import ${i}" } ++ Seq("import Domain exposing (..)", "import Json.Decode exposing (..)")).mkString("\n") + "\n\n\n"
  }

  def generateBuiltinTypeDecoders(domain: Domain): String = {
    val p: Seq[String] = domain.getDomainTypes.flatMap { dt => dt.getFields }.flatMap {
      _.getType match {
        case LocalDateTimeType => Some(
          s"""date : Decoder Date.Date
             |date =
             |    string
             |        |> andThen
             |            (\\s ->
             |                case (Date.fromString s) of
             |                    Ok result ->
             |                        succeed result
             |
 |                    Err message ->
             |                        fail message
             |            )
             |""".stripMargin)
        case LocalTimeType => Some("Time")
        case _ => None
      }
    }
    p.distinct.sorted.mkString("\n\n\n") + "\n\n"
  }

  def generateSingleType(dt: DomainType): String = {
    dt match {
      case d: DataContainer =>
        s"""{-| -}
           |${StringUtils.downcaseFirst(d.getName)} : Decoder ${d.getName}
           |${StringUtils.downcaseFirst(d.getName)} =
           |    map${d.getElmFields.length} ${d.getName} ${d.getElmFields.map(f => s"""(field "${f.name}" ${f.decoder})""").mkString(" ")}""".stripMargin
      case e: Enumeration =>
        s"""{-| -}
           |${StringUtils.downcaseFirst(e.getName)} : Decoder ${e.getName}
           |${StringUtils.downcaseFirst(e.getName)} =
           |    string
           |        |> andThen
           |            (\\s ->
           |                case s of
           |${
          e.getMembers.map(m =>
            s"""                    "${m}" ->
               |                        succeed ${StringUtils.upcaseFirst(StringUtils.ordinaryTextToCamelCase(m))}""".stripMargin).mkString("\n\n")
        }
           |
           |                    other ->
           |                        fail ("Expected ${e.getName}, but was '" ++ other ++ "'.")
           |            )""".stripMargin
    }
  }
}

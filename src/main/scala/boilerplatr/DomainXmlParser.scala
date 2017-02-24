package boilerplatr

import scala.collection.immutable.Seq
import scala.xml.{Elem, Node, NodeSeq}

object DomainXmlParser {
  def fromXml(xml: Elem): DomainEl = {
    val rootpackage: String = (xml \\ "domain" \ "meta" \ "rootpackage").text
    val domaintypes: NodeSeq = xml \\ "domain" \ "domaintypes" \ "_"
    val domaintypeobjects: Seq[DomainTypeEl] = domaintypes.map {
      case e @ <entity>{_*}</entity> => EntityEl(
        (e \ "@name").text,
        (e \ "doc").text,
        (e \ "fields" \ "field").map(toField),
        (e \ "extends" \ "@supertype").text match {
          case "" => None
          case superTypeName => Some(superTypeName)
        },
        (e \ "examples" \ "example").map(toExample))
      case e @ <valueobject>{_*}</valueobject> => ValueObjectEl(
        (e \ "@name").text,
        (e \ "doc").text,
        (e \ "fields" \ "field").map(toField),
        (e \ "extends" \ "@supertype").text match {
          case "" => None
          case superTypeName => Some(superTypeName)
        },
        (e \ "examples" \ "example").map(toExample))
      case e @ <enumeration>{_*}</enumeration> => EnumerationEl((e \ "@name").text, (e \ "doc").text, (e \ "member").map(m => MemberEl(m.text)))
    }
    val aggregates: NodeSeq = xml \\ "domain" \ "aggregates" \ "_"
    val aggregateEls: Seq[AggregateEl] = aggregates.map {
      case e @ <aggregate>{_*}</aggregate> => AggregateEl(
        (e \ "@name").text,
        (e \ "@rootEntity").text,
        (e \ "@rootHasId").text.toBoolean,
        (e \ "component").map(toAggregateComponent)
      )
    }
    DomainEl(rootpackage, domaintypeobjects, aggregateEls)
  }

  private def toField(n: Node): FieldEl = {
    val name = (n \ "@name").text
    val type_ = (n \ "@type").text
    val ref = (n \ "@ref").text
    val listRef = (n \ "@list-ref").text
    val optional = (n \ "@optional").text
    val doc = (n \ "doc").text
    FieldEl(name, type_, ref, listRef, optional, doc)
  }

  private def toExample(n: Node): ExampleEl = {
    val name = (n \ "@name").text
    val fieldValues = (n \ "fieldvalue").map(toFieldValue)
    ExampleEl(name, fieldValues)
  }

  private def toFieldValue(n: Node): FieldValueEl = {
    val field = (n \ "@field").text
    val ref = (n \ "@ref").text
    val value = n.text
    FieldValueEl(field, ref, value)
  }

  private def toAggregateComponent(n: Node): AggregateComponentEl = {
    val name = (n \ "@name").text
    val hasId = (n \ "@hasId").text.toBoolean
    val entity = (n \ "@entity").text
    AggregateComponentEl(name, hasId, entity)
  }
}

package viiksiniekka

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
    val repositories: NodeSeq = xml \\ "domain" \ "repositories" \ "_"
    val repositoryEls: Seq[RepositoryEl] = repositories.map {
      case r @ <repository>{_*}</repository> => {
        val reads: NodeSeq = r \ "read"
        val readEls: Seq[ReadEl] = reads.map(rd => ReadEl(
          (rd \ "@name").text,
          (rd \ "@where").text,
          (rd \ "@orderBy").text,
          TypeRef.fromString((rd \ "@output").text)
        ))
        val operationEls: Seq[RepositoryOperationEl] = readEls
        RepositoryEl(
          (r \ "@name").text,
          operationEls
        )
      }
    }
    DomainEl(rootpackage, domaintypeobjects, aggregateEls, repositoryEls)
  }

  private def toField(n: Node): FieldEl = {
    val name = (n \ "@name").text
    val type_ = (n \ "@type").text
    val optional = (n \ "@optional").text
    val doc = (n \ "doc").text
    FieldEl(name, TypeRef.fromString(type_), optional, doc)
  }

  private def toExample(n: Node): ExampleEl = {
    val name = (n \ "@name").text
    val fieldValues = (n \ "fieldvalue").map(toFieldValue)
    ExampleEl(name, fieldValues)
  }

  private def toFieldValue(n: Node): FieldValueEl = {
    val field = (n \ "@field").text
    val ref = (n \ "@ref").text
    val list = toFieldValueList(n \ "list")
    val value = n.text
    FieldValueEl(field, ref, value, list)
  }

  private def toFieldValueList(n: NodeSeq): Seq[ListEntryEl] = {
    if (n.isEmpty) {
      Seq()
    } else {
      (n \ "entry").map(toListEntry)
    }
  }

  private def toListEntry(n: Node): ListEntryEl = {
    ListEntryEl((n \ "@ref").text)
  }

  private def toAggregateComponent(n: Node): AggregateComponentEl = {
    val name = (n \ "@name").text
    val hasId = toBooleanWithDefaultFalse((n \ "@hasId").text)
    val entity = (n \ "@object").text
    AggregateComponentEl(name, hasId, entity)
  }

  private def toBooleanWithDefaultFalse(text: String) = {
    if (text.isEmpty) {
      false
    } else {
      text.toBoolean
    }
  }
}

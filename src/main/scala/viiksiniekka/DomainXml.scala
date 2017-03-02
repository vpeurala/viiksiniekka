package viiksiniekka

case class DomainEl(rootPackage: String, domainTypes: Seq[DomainTypeEl], aggregates: Seq[AggregateEl]) {
  override def toString: String = {
    s"DomainEl(rootPackage: ${rootPackage},\ndomainTypes: ${domainTypes.mkString("\n  ")},\naggregates: ${aggregates.mkString("\n  ")}"
  }
}

sealed trait DomainTypeEl {
  def name: String

  def doc: String
}

sealed trait DataContainerEl extends DomainTypeEl {
  def fields: Seq[FieldEl]

  def extends_ : Option[String]
}

case class EntityEl(name: String, doc: String, fields: Seq[FieldEl], extends_ : Option[String], examples: Seq[ExampleEl]) extends DataContainerEl

case class ValueObjectEl(name: String, doc: String, fields: Seq[FieldEl], extends_ : Option[String], examples: Seq[ExampleEl]) extends DataContainerEl

case class EnumerationEl(name: String, doc: String, members: Seq[MemberEl]) extends DomainTypeEl

case class FieldEl(name: String, type_ : String, ref: String, listRef: String, optional: String, documentation: String)

case class MemberEl(name: String)

case class ExampleEl(name: String, fieldValues: Seq[FieldValueEl])

case class FieldValueEl(field: String, ref: String, value: String)

case class AggregateEl(name: String, rootEntity: String, rootHasId: Boolean, components: Seq[AggregateComponentEl])

case class AggregateComponentEl(name: String, hasId: Boolean, entity: String)
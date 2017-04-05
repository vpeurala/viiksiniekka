package viiksiniekka

class Domain(rootPackage: Package, domainTypes: Seq[DomainType], aggregates: Seq[Aggregate], repositories: Seq[Repository]) {
  def exampleContaining(example: Example): Example = {
    val examplesContaining: Seq[Example] = getExamples.filter { ex =>
      ex.fieldValues.exists {
        case ListFieldValue(field, entries) => {
          entries.exists { le =>
            le.example == example
          }
        }
        case _ => false
      }
    }
    if (examplesContaining.isEmpty) {
      throw new IllegalArgumentException(s"No example found containing example '${example}'.")
    } else if (examplesContaining.size == 1) {
      examplesContaining.head
    } else {
      throw new IllegalStateException(s"More than one example found containing '${example}': ${examplesContaining}")
    }
  }

  def exampleForName(name: String): Example = {
    val examplesForName = getExamples.filter(_.name == name)
    if (examplesForName.isEmpty) {
      throw new IllegalArgumentException(s"No example found for name '${name}'.")
    } else if (examplesForName.size == 1) {
      examplesForName.head
    } else {
      throw new IllegalStateException(s"More than one example found for name '${name}': ${examplesForName}")
    }
  }

  def domainTypeForExample(example: Example): DataContainer = {
    getDataContainers.find { dc =>
      dc.getExamples.contains(example)
    }.get
  }

  def getDomainTypes: Seq[DomainType] = domainTypes

  def getAggregates: Seq[Aggregate] = aggregates

  def getRepositories: Seq[Repository] = repositories

  def getRootPackage: Package = rootPackage

  def getEntities: Seq[Entity] = {
    domainTypes.filter {
      case e: Entity => true
      case _ => false
    }.map(_.asInstanceOf[Entity])
  }

  def getValueObjects: Seq[ValueObject] = {
    domainTypes.filter {
      case v: ValueObject => true
      case _ => false
    }.map(_.asInstanceOf[ValueObject])
  }

  def getDataContainers: Seq[DataContainer] = {
    getEntities ++ getValueObjects
  }

  def entityForName(name: String): Entity = {
    domainTypes.find { p =>
      p.isInstanceOf[Entity] && p.getName == name
    }.get.asInstanceOf[Entity]
  }

  def getExamples: Seq[Example] = {
    domainTypes.flatMap { dt =>
      dt.getExamples
    }
  }
}

trait Nameable {
  def getName: String
}

trait Documentable {
  def getDocumentation: String
}

trait HasFields {
  def getFields: Seq[Field]

  def getDeclaredFields: Seq[Field]
}

trait HasPackage {
  def getPackage: Package
}

trait HasType {
  def getType: Type
}

trait HasExamples {
  def getExamples: Seq[Example]
}

sealed trait Package {
  def getBuilderPackage: OrdinaryPackage

  def getDataPackage: OrdinaryPackage

  def getExamplePackage: OrdinaryPackage

  def getVisitorPackage: OrdinaryPackage

  def getRepositoryPackage: OrdinaryPackage
}

case class OrdinaryPackage(name: String) extends Package {
  override def getBuilderPackage = OrdinaryPackage(name = name + ".domain.builder")

  override def getDataPackage = OrdinaryPackage(name = name + ".domain.data")

  override def getExamplePackage = OrdinaryPackage(name = name + ".domain.example")

  override def getVisitorPackage = OrdinaryPackage(name = name + ".domain.visitor")

  override def getRepositoryPackage = OrdinaryPackage(name = name + ".domain.repository")
}

case object PredefPackage extends Package {
  override def getBuilderPackage: OrdinaryPackage = OrdinaryPackage(name = "domain.builder")

  override def getDataPackage = OrdinaryPackage(name = "domain.data")

  override def getExamplePackage = OrdinaryPackage(name = "domain.example")

  override def getVisitorPackage = OrdinaryPackage(name = "domain.visitor")

  override def getRepositoryPackage = OrdinaryPackage(name = "domain.repository")
}

sealed trait Type extends Nameable with HasPackage

sealed trait DomainType extends Type with Documentable with HasFields with HasExamples

case class ExternalType(name: String, package_ : Package) extends Type {
  override def getName: String = name

  override def getPackage: Package = package_
}

case object StringType extends Type {
  override def getName: String = "String"

  override def getPackage: Package = PredefPackage
}

case object IntegerType extends Type {
  override def getName: String = "Integer"

  override def getPackage: Package = PredefPackage
}

case object LongType extends Type {
  override def getName: String = "Long"

  override def getPackage: Package = PredefPackage
}

case object BooleanType extends Type {
  override def getName: String = "Boolean"

  override def getPackage: Package = PredefPackage
}

case object LocalTimeType extends Type {
  override def getName: String = "LocalTime"

  override def getPackage: Package = OrdinaryPackage("java.time")
}

case object LocalDateTimeType extends Type {
  override def getName: String = "LocalDateTime"

  override def getPackage: Package = OrdinaryPackage("java.time")
}

// TODO vpeurala 8.3.2017: The correct implementation of
// getName and getPackage is a little unclear;
// currently they just delegate to the contained type
case class ListType(containedType: Type) extends Type {
  override def getName: String = containedType.getName

  override def getPackage: Package = containedType.getPackage
}

sealed trait DataContainer extends DomainType {
  def name: String

  def documentation: String

  def package_ : Package

  def declaredFields: Seq[Field]

  def extends_ : Option[DataContainer]

  def examples: Seq[Example]

  def getExtends: Option[DataContainer]

  def copy(name: String = name,
           documentation: String = documentation,
           package_ : Package = package_,
           declaredFields: Seq[Field] = declaredFields,
           extends_ : Option[DataContainer] = extends_,
           examples: Seq[Example] = examples): DataContainer
}

case class Entity(name: String, documentation: String, package_ : Package, declaredFields: Seq[Field], extends_ : Option[DataContainer], examples: Seq[Example]) extends DataContainer {
  private val idField = OrdinaryField(
    name = "id",
    documentation = "Surrogate key.",
    optional = false,
    type_ = LongType)

  private val allFields = extends_ match {
    case None => Seq(idField) ++
      declaredFields
    case Some(e: DataContainer) => e.getFields ++ declaredFields
  }

  override def getName: String = name

  override def getDocumentation: String = documentation

  override def getPackage: Package = package_

  override def getFields: Seq[Field] = allFields

  override def getDeclaredFields: Seq[Field] = extends_ match {
    case None => Seq(idField) ++ declaredFields
    case Some(_) => declaredFields
  }

  override def getExtends: Option[DataContainer] = extends_

  override def getExamples: Seq[Example] = examples

  override def copy(name: String = this.name,
                    documentation: String = this.documentation,
                    package_ : Package = this.package_,
                    declaredFields: Seq[Field] = this.declaredFields,
                    extends_ : Option[DataContainer] = this.extends_,
                    examples: Seq[Example] = this.examples): DataContainer = {
    Entity(name, documentation, package_, declaredFields, extends_, examples)
  }
}

case class ValueObject(name: String, documentation: String, package_ : Package, declaredFields: Seq[Field], extends_ : Option[DataContainer], examples: Seq[Example]) extends DataContainer {
  private val allFields = extends_ match {
    case None => declaredFields
    case Some(e: DataContainer) => e.getFields ++ declaredFields
  }

  override def getName: String = name

  override def getDocumentation: String = documentation

  override def getPackage: Package = package_

  override def getFields: Seq[Field] = allFields

  override def getDeclaredFields: Seq[Field] = declaredFields

  override def getExtends: Option[DataContainer] = extends_

  override def getExamples: Seq[Example] = examples

  override def copy(name: String,
                    documentation: String,
                    package_ : Package,
                    declaredFields: Seq[Field],
                    extends_ : Option[DataContainer],
                    examples: Seq[Example]): DataContainer = {
    ValueObject(name, documentation, package_, declaredFields, extends_, examples)
  }
}

case class Enumeration(name: String, documentation: String, package_ : Package, members: Seq[String]) extends DomainType {
  private val allFields = Seq(new OrdinaryField(
    name = "value",
    documentation = "Enumeration member value.",
    optional = false,
    type_ = StringType))

  override def getName: String = name

  override def getDocumentation: String = documentation

  override def getPackage: Package = package_

  override def getFields: Seq[Field] = allFields

  override def getDeclaredFields: Seq[Field] = getFields

  override def getExamples: Seq[Example] = Seq()

  def getMembers: Seq[String] = members
}

trait PossiblyOptional {
  def isOptional: Boolean
}

sealed trait Field extends Nameable with Documentable with HasType with PossiblyOptional {
  def copy(
            name: String = getName,
            documentation: String = getDocumentation,
            optional: Boolean = isOptional,
            type_ : Type = getType): Field
}

case class OrdinaryField(name: String, documentation: String, optional: Boolean, type_ : Type) extends Field {
  override def getName: String = name

  override def getDocumentation: String = documentation

  override def isOptional: Boolean = optional

  override def getType: Type = type_

  override def copy(name: String, documentation: String, optional: Boolean, type_ : Type): Field = {
    OrdinaryField(name, documentation, optional, type_)
  }
}

case class ListField(name: String, documentation: String, type_ : Type) extends Field {
  override def getName: String = name

  override def getDocumentation: String = documentation

  override def getType: Type = type_

  override def isOptional: Boolean = false

  override def copy(name: String,
                    documentation: String,
                    optional: Boolean,
                    type_ : Type): Field = {
    ListField(name, documentation, type_)
  }
}

sealed trait Value

case class Example(name: String, fieldValues: Seq[FieldValue]) extends Value {
  def getValueForFieldNamed(domain: Domain)(fieldName: String): Value = {
    fieldValues.find(fv => fv.getField.getName == fieldName).getOrElse(new FieldValue {
      override def getValue(domain: Domain) = Null

      override def getField = throw new UnsupportedOperationException("getField")
    }).getValue(domain)
  }
}

case class SimpleValue(value: String) extends Value

case class ListValue(value: Seq[Value]) extends Value

case object Null extends Value

sealed trait FieldValue {
  def getField: Field

  def getValue(domain: Domain): Value
}

case class SimpleFieldValue(field: Field, value: String) extends FieldValue {
  override def getField: Field = field

  override def getValue(domain: Domain): Value = SimpleValue(value)
}

case class ReferenceFieldValue(field: Field, example: Example) extends FieldValue {
  override def getField: Field = field

  override def getValue(domain: Domain): Value = example
}

case class ListFieldValue(field: Field, entries: Seq[ListEntry]) extends FieldValue {
  override def getField: Field = field

  override def getValue(domain: Domain): Value = throw new UnsupportedOperationException("getValue")
}

case class ListEntry(example: Example)

case class Aggregate(name: String, documentation: String, package_ : Package, rootEntity: Entity, rootHasId: Boolean, components: Seq[AggregateComponent]) {
  def aliasOfDataContainerInThisAggregate(d: DataContainer): Option[String] = {
    if (rootEntity == d) {
      Some(name)
    } else {
      components.find(p => p.dataContainer == d).map(_.name)
    }
  }

  def getComponents: Seq[DataContainer] = {
    val otherComponents: Seq[DataContainer] = components.map(getComponent)
    val rootComponent: DataContainer = getComponent(AggregateComponent(name, documentation, rootEntity, rootHasId))
    rootComponent +: otherComponents
  }

  def getComponent(ac: AggregateComponent): DataContainer = {
    if (ac.hasId) {
      getComponentWithId(ac)
    } else {
      getComponentWithoutId(ac)
    }
  }

  def getComponentWithId(ac: AggregateComponent): DataContainer = {
    val d: DataContainer = ac.dataContainer
    val declaredFields: Seq[Field] = d.getDeclaredFields.filter(f => f.getName != "id").flatMap { f: Field =>
      f.getType match {
        case e: Entity => if (isIncludedInThisAggregate(e)) {
          Some(f match {
            case o: OrdinaryField => o.copy(type_ = o.getType.asInstanceOf[DataContainer].copy(name = aliasOfDataContainerInThisAggregate(e).get))
            case l: ListField => l.copy(type_ = l.getType.asInstanceOf[DataContainer].copy(name = aliasOfDataContainerInThisAggregate(e).get))
          })
        } else {
          f match {
            case o: OrdinaryField => Some(o.copy(type_ = LongType))
            case l: ListField => None
          }
        }
        case v: ValueObject => if (isAliasedInThisAggregate(v)) {
          Some(f.copy(type_ = v.copy(name = aliasOfDataContainerInThisAggregate(v).get)))
        } else {
          Some(f)
        }
        case _ => Some(f)
      }
    }
    d.copy(name = ac.name, declaredFields = declaredFields)
  }

  def getComponentWithoutId(ac: AggregateComponent): DataContainer = {
    val d: DataContainer = ac.dataContainer
    ValueObject(ac.name, d.documentation, d.package_, d.getDeclaredFields.filter(f => f.getName != "id").flatMap { f: Field =>
      f.getType match {
        case e: Entity => if (isIncludedInThisAggregate(e)) {
          Some(f match {
            case o: OrdinaryField => o.copy(type_ = o.getType.asInstanceOf[DataContainer].copy(name = aliasOfDataContainerInThisAggregate(e).get))
            case l: ListField => l.copy(type_ = l.getType.asInstanceOf[DataContainer].copy(name = aliasOfDataContainerInThisAggregate(e).get))
          })
        } else {
          f match {
            case o: OrdinaryField => Some(o.copy(type_ = LongType))
            case l: ListField => None
          }
        }
        case v: ValueObject => if (isAliasedInThisAggregate(v)) {
          Some(f.copy(type_ = v.copy(name = aliasOfDataContainerInThisAggregate(v).get)))
        } else {
          Some(f)
        }
        case _ => Some(f)
      }
    }, d.extends_, d.examples)
  }

  def isIncludedInThisAggregate(e: DataContainer): Boolean = {
    rootEntity == e || components.exists(p => p.dataContainer == e)
  }

  def isAliasedInThisAggregate(v: ValueObject): Boolean = {
    aliasOfDataContainerInThisAggregate(v).isDefined
  }
}

case class AggregateComponent(name: String, documentation: String, dataContainer: DataContainer, hasId: Boolean)

case class Repository(name: String, operations: Seq[RepositoryOperation])

sealed trait RepositoryOperation {
  def name: String
}

case class ReadRepositoryOperation(name: String, where: String, orderBy: String, output: Type) extends RepositoryOperation

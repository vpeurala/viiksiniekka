package viiksiniekka

import viiksiniekka.graph.MutableGraph

import scala.collection.mutable

object DomainXmlTransformer {
  def toDomain(domainEl: DomainEl): Domain = {
    val domainTypes = makeDomainTypes(domainEl)
    val aggregates = makeAggregates(domainEl, domainTypes)
    new Domain(domainTypes, aggregates)
  }

  private def makeDomainTypes(domainEl: DomainEl): Seq[DomainType] = {
    var typeTable = mutable.LinkedHashMap[String, DomainType]()
    val domainTypeEls: Seq[DomainTypeEl] = sortDomainTypes(domainEl.domainTypes)
    domainTypeEls.foreach { domainTypeEl: DomainTypeEl =>
      typeTable += makeDomainType(domainEl)(typeTable)(domainTypeEl)
    }
    typeTable.values.toSeq
  }

  def sortDomainTypes(domainTypes: Seq[DomainTypeEl]): Seq[DomainTypeEl] = {
    val domainTypeNames: Set[String] = domainTypes.map(_.name).toSet
    val dependencyGraph: MutableGraph[String] = new MutableGraph[String]
    domainTypes.foreach {
      dt => dependencyGraph.addNode(dt.name)
    }
    domainTypes.foreach {
      case d: DataContainerEl => {
        d.extends_.foreach { ex =>
          if (domainTypeNames.contains(ex)) {
            dependencyGraph.addEdge(d.name, ex)
          }
        }
        d.fields.foreach { f =>
          if (domainTypeNames.contains(f.typeRef.baseTypeName)) {
            dependencyGraph.addEdge(d.name, f.typeRef.baseTypeName)
          }
        }
      }
      case e: EnumerationEl => ()
    }
    val domainTypeNamesInReverseDependencyOrder: Seq[String] = dependencyGraph.topSort.reverse
    domainTypeNamesInReverseDependencyOrder.map(n => domainTypes.find(_.name == n).get)
  }

  private def makeAggregates(domainEl: DomainEl, domainTypes: Seq[DomainType]): Seq[Aggregate] = {
    domainEl.aggregates.map { aggregateEl: AggregateEl =>
      makeAggregate(domainEl)(aggregateEl)(domainTypes)
    }
  }

  private def makeDomainType
  (domainEl: DomainEl)
  (typeTable: mutable.LinkedHashMap[String, DomainType])
  (domainTypeEl: DomainTypeEl): (String, DomainType) = {
    def makeDeclaredFields(fields: Seq[FieldEl]) = {
      fields.map(field => {
        if (field.typeRef.isList) {
          ListField(
            name = field.name,
            documentation = field.documentation,
            type_ = typeTable(field.typeRef.baseTypeName))
        } else {
          OrdinaryField(
            name = field.name,
            documentation = field.documentation,
            optional = field.optional == "true",
            type_ = if (typeTable.contains(field.typeRef.baseTypeName)) {
              typeTable(field.typeRef.baseTypeName)
            }
            else {
              field.typeRef.baseTypeName match {
                case "String" => StringType
                case "LocalTime" => LocalTimeType
                case "Boolean" => BooleanType
                case "Integer" => IntegerType
                case "LocalDateTime" => LocalDateTimeType
              }
            }
          )
        }
      })
    }

    def makeSuperTypeOpt(extends_ : Option[String]): Option[DataContainer] = {
      extends_ match {
        case None => None
        case Some(superTypeName) => Some(typeTable(superTypeName).asInstanceOf[DataContainer])
      }
    }

    def makeExamples(declaredFields: Seq[Field], superTypeOpt: Option[DataContainer])(exampleEls: Seq[ExampleEl]): Seq[Example] = {
      exampleEls.map(
        exampleEl => Example(
          exampleEl.name,
          exampleEl.fieldValues.map(fieldValueEl => {
            val field: Field = (declaredFields ++ superTypeOpt.map(_.getFields).getOrElse(Seq())).find(f => f.getName == fieldValueEl.field).getOrElse(
              throw new IllegalArgumentException(s"Field '${fieldValueEl.field}' not found for example element: '${exampleEl}'."))
            if (!fieldValueEl.ref.isEmpty) {
              ReferenceFieldValue(field, fieldValueEl.ref)
            } else {
              SimpleFieldValue(field, fieldValueEl.value)
            }
          })))
    }

    domainTypeEl match {
      case EntityEl(name, doc, fields, extends_, exampleEls) =>
        val declaredFields = makeDeclaredFields(fields)
        val superTypeOpt = makeSuperTypeOpt(extends_)
        (name, Entity(
          name = name,
          documentation = doc,
          package_ = OrdinaryPackage(domainEl.rootPackage),
          declaredFields = declaredFields,
          extends_ = superTypeOpt,
          examples = makeExamples(declaredFields, superTypeOpt)(exampleEls)))
      case ValueObjectEl(name, doc, fields, extends_, exampleEls) =>
        val declaredFields = makeDeclaredFields(fields)
        val superTypeOpt = makeSuperTypeOpt(extends_)
        (name, ValueObject(
          name = name,
          documentation = doc,
          package_ = OrdinaryPackage(domainEl.rootPackage),
          declaredFields = declaredFields,
          extends_ = superTypeOpt,
          examples = makeExamples(declaredFields, superTypeOpt)(exampleEls)))
      case EnumerationEl(name, doc, members) =>
        (name, Enumeration(name, doc, OrdinaryPackage(domainEl.rootPackage), members.map(_.name)))
    }
  }

  def makeAggregate(domainEl: DomainEl)(aggregateEl: AggregateEl)(domainTypes: Seq[DomainType]): Aggregate = {
    val rootEntity: Entity = domainTypes.find(dt => dt.getName == aggregateEl.rootEntity) match {
      case None => throw new IllegalArgumentException("Root entity not found: '" + aggregateEl.rootEntity + "'.")
      case Some(d: Entity) => d
      case Some(o) => throw new IllegalArgumentException("Root entity of wrong kind: " + o.getClass)
    }
    val components: Seq[AggregateComponent] = aggregateEl.components.map { componentEl =>
      val name: String = componentEl.name
      val domainObject: DataContainer = domainTypes.find(dt => dt.getName == componentEl.entity) match {
        case None => throw new IllegalArgumentException("Domain type not found: '" + componentEl.entity + "'.")
        case Some(d: DataContainer) => d
        case Some(o) => throw new IllegalArgumentException("Domain type of wrong kind: " + o.getClass)
      }
      AggregateComponent(name, "TODO: docs missing", domainObject, componentEl.hasId) // TODO: docs missing
    }
    Aggregate(aggregateEl.name, documentation = "TODO: docs missing", // TODO: docs missing
      package_ = OrdinaryPackage(domainEl.rootPackage), rootEntity, rootHasId = aggregateEl.rootHasId, components)
  }
}

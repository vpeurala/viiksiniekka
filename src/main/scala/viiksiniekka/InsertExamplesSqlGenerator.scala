package viiksiniekka

import viiksiniekka.StringUtils.{camelCaseToSnakeCase, indent}
import viiksiniekka.TopologicalSort.topSort

class InsertExamplesSqlGenerator extends Generator {
  def generateSum(d: Domain): String = {
    val topLevelEntities: Seq[Entity] = d.getDomainTypes.filter { p: DomainType =>
      p match {
        case e: Entity => isTopLevelEntity(d)(e)
        case _ => false
      }
    }.asInstanceOf[Seq[Entity]]
    val topSorted: Seq[Entity] = topSort(d)(topLevelEntities)
    topSorted.map(entitySource(d)).mkString("\n")
  }

  override def generate(d: Domain): Map[String, String] = {
    val elems = d.getEntities.flatMap { e: Entity =>
      if (isTopLevelEntity(d)(e)) {
        Some(sourceFileName(e.getName), entitySource(d)(e))
      } else {
        None
      }
    }

    Map(elems: _*)
  }

  private def isTopLevelEntity(d: Domain)(e: Entity): Boolean = e.getExtends.isEmpty

  def getTopLevelEntity(d: Domain)(e: Entity): Entity = {
    if (isTopLevelEntity(d)(e)) {
      e
    } else {
      getTopLevelEntity(d)(e.extends_.get.asInstanceOf[Entity])
    }
  }

  def getTableName(e: DataContainer): String = {
    e.getExtends match {
      case None => camelCaseToSnakeCase(e.getName)
      case Some(parent) => getTableName(parent.asInstanceOf[DataContainer])
    }
  }

  private def sourceFileName(name: String): String = {
    ProjectStructure.SrcMainResources + "/ddl/" + camelCaseToSnakeCase(name) + "_insert.sql"
  }

  def isSubclassOfDeep(d: Domain)(parent: DataContainer, child: DataContainer): Boolean = {
    child.getExtends match {
      case Some(superType: DataContainer) => (superType == parent) || (superType.getExtends.isDefined && isSubclassOfDeep(d)(parent, superType))
      case None => false
    }
  }

  def getSubclassesDeep(d: Domain)(e1: DataContainer): Seq[DataContainer] = {
    d.getDomainTypes.filter(p => p match {
      case e2: Entity => isSubclassOfDeep(d)(e1, e2)
      case v1: ValueObject => isSubclassOfDeep(d)(e1, v1)
      case _: Enumeration => false
    }).asInstanceOf[Seq[DataContainer]]
  }

  def getSubclassFields(d: Domain)(e: DataContainer): Seq[Field] = {
    getSubclassesDeep(d)(e).flatMap(f => f.getDeclaredFields)
  }

  def isOnManySideOfListRelation(d: Domain)(e: DataContainer): Boolean = {
    d.getDomainTypes.exists(p => p.getDeclaredFields.exists {
      case ListField(_, _, type_) => type_ == e
      case _ => false
    })
  }

  def getOneSideOfListRelation(d: Domain)(manySide: DataContainer): Option[Column] = {
    if (isOnManySideOfListRelation(d)(manySide)) {
      d.getDomainTypes.find(p => p.getDeclaredFields.exists {
        case ListField(_, _, type_) => type_ == manySide
        case _ => false
      }) match {
        case Some(p: DataContainer) => Some(Column(
          getTableName(p),
          SqlType("BIGINT"),
          notNull = true,
          isPrimaryKey = false,
          foreignKeyConstraint = Option.empty,
          Seq()))
        case None => None
      }
    } else {
      None
    }
  }

  def getColumnsForField
  (d: Domain)
  (namePrefix: String,
   optionalPrefix: Boolean,
   fieldsPrefix: Seq[Field])(f: Field): Seq[Column] = {
    f match {
      case of@OrdinaryField(name, docs, optional, vo: ValueObject) =>
        getColumnsInner(d)(
          namePrefix = if (namePrefix.isEmpty) {
            camelCaseToSnakeCase(name)
          } else {
            namePrefix + "_" + camelCaseToSnakeCase(name)
          },
          optionalPrefix = optionalPrefix || optional,
          fieldsPrefix = fieldsPrefix :+ of)(vo)
      case of@OrdinaryField(name, docs, optional, type_) =>
        Seq(Column(
          if (namePrefix.isEmpty) {
            camelCaseToSnakeCase(name)
          } else {
            namePrefix + "_" + camelCaseToSnakeCase(name)
          },
          sqlType(of),
          !(optionalPrefix || optional),
          name == "id",
          None, // TODO missing constraint
          fieldsPrefix :+ of))
      case ListField(_, _, _) => Seq()
    }
  }

  def getColumnsInner(d: Domain)(namePrefix: String, optionalPrefix: Boolean, fieldsPrefix: Seq[Field])(e: DataContainer): Seq[Column] = {
    e.getFields.flatMap(getColumnsForField(d)(namePrefix, optionalPrefix, fieldsPrefix)) ++
      getSubclassFields(d)(e).flatMap(getColumnsForField(d)(namePrefix, optionalPrefix, fieldsPrefix)) ++ {
      if (isOnManySideOfListRelation(d)(e)) {
        getOneSideOfListRelation(d)(e) match {
          case Some(column) => Seq(column)
          case None => Seq()
        }
      } else {
        Seq()
      }
    }
  }

  def getColumns(d: Domain)(e: DataContainer): Seq[Column] = {
    getColumnsInner(d)(namePrefix = "", optionalPrefix = false, fieldsPrefix = Seq())(e)
  }

  def sqlType(of: OrdinaryField): SqlType = SqlType(of.type_ match {
    case BooleanType => "BOOLEAN"
    case IntegerType => "INT"
    case LongType => "BIGINT"
    case StringType => "VARCHAR"
    case LocalDateTimeType => "TIMESTAMP WITHOUT TIME ZONE"
    case _: DomainType => "BIGINT"
  })

  def entitySource(d: Domain)(e: Entity): String = {
    val table: Table = getTable(d)(e)
    s"""INSERT INTO ${table.name} (
       |${indent(2)(table.columns.map(_.name).mkString(",\n"))}
       |) VALUES ${(e +: getSubclassesDeep(d)(e)).flatMap(_.getExamples).map(exampleToInsertValuesGroup(d)(table)).map(g => s"(\n  ${g}\n)").mkString(", ")};
       |""".stripMargin
  }

  def getTable(d: Domain)(e: Entity): Table = {
    Table(getTableName(e), getColumns(d)(e))
  }

  def toTimestamp(value: String): String = {
    if (!value.matches("\\d{2}.\\d{2}.\\d{4} \\d{2}:\\d{2}")) {
      throw new IllegalArgumentException(s"Illegal value '${value}' for LocalTime field: the required pattern is HH:mm.")
    }
    val parts = value.split("[\\.: ]")
    val day = "%02d".format(parts(0).toInt)
    val month = "%02d".format(parts(1).toInt)
    val year = "%04d".format(parts(2).toInt)
    val hour = "%02d".format(parts(3).toInt)
    val minute = "%02d".format(parts(4).toInt)
    s"'${year}-${month}-${day} ${hour}:${minute}:00'"
  }

  def getFieldFromExample(domain: Domain)(example: Example)(fields: Seq[Field]): String = {
    val valueOfHeadField: Value = example.getValueForFieldNamed(domain)(fields.head.getName)
    if (fields.tail.isEmpty) {
      valueOfHeadField match {
        case SimpleValue(value) => fields.head.getType match {
          case StringType => s"'${value}'"
          case LocalDateTimeType => toTimestamp(value)
          case e: Enumeration => s"'${value}'"
          case _ => value
        }
        case e: Example => exampleToSelect(domain)(e)
        case Null => "NULL"
      }
    } else {
      valueOfHeadField match {
        case SimpleValue(value) => throw new IllegalArgumentException(s"Simple value found too soon: ${value}, path remaining: ${fields.tail}")
        case e: Example => getFieldFromExample(domain)(e)(fields.tail)
        case Null => "NULL"
      }
    }
  }

  def exampleToSelect(domain: Domain)(example: Example): String = {
    val dt: DataContainer = domain.domainTypeForExample(example)
    dt match {
      case _: ValueObject => throw new UnsupportedOperationException(s"exampleToSelect not supported for value objects: ${example}")
      case entity: Entity => {
        val table = getTable(domain)(entity)
        val whereClause = s"id = ${getExampleId(domain)(example)} /* ${example.name} */"
        s"SELECT id FROM ${table.name} WHERE ${whereClause}"
      }
    }
  }

  def getExampleId(domain: Domain)(example: Example): Int = {
    val entity: Entity = getTopLevelEntity(domain)(domain.domainTypeForExample(example).asInstanceOf[Entity])
    val examplesInSameTable: Seq[Example] = (entity +: getSubclassesDeep(domain)(entity)).flatMap(_.getExamples)
    examplesInSameTable.indexOf(example) + 1
  }

  def exampleToInsertValuesGroup(d: Domain)(table: Table)(example: Example): String = {
    table.columns.flatMap(column => {
      if (column.isPrimaryKey) {
        Some(s"${getExampleId(d)(example)} /* ${example.name} */")
      } else {
        if (column.fields.isEmpty) {
          // One side of one-to-many list example
          Some(exampleToSelect(d)(d.exampleContaining(example)))
        } else {
          Some(getFieldFromExample(d)(example)(column.fields))
        }
      }
    }).mkString(",\n  ")
  }
}

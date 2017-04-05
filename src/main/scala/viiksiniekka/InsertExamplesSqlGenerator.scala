package viiksiniekka

import viiksiniekka.StringUtils.{camelCaseToSnakeCase, indent}
import viiksiniekka.graph.MutableGraph

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

  private def topSort(d: Domain)(entities: Seq[Entity]): Seq[Entity] = {
    val dependencyGraph: MutableGraph[String] = new MutableGraph[String]
    val tableNames: Seq[String] = entities.map(getTableName)
    tableNames.foreach { t => dependencyGraph.addNode(t) }
    entities.foreach { e =>
      def handleField(f: Field): Unit = {
        f match {
          case l: ListField => ()
          case o: OrdinaryField if o.getType.isInstanceOf[DataContainer] =>
            if (tableNames.contains(getTableName(o.getType.asInstanceOf[DataContainer]))) {
              dependencyGraph.addEdge(getTableName(e), getTableName(o.getType.asInstanceOf[DataContainer]))
            }
            o.getType.asInstanceOf[DataContainer].getFields.foreach(handleField)
          case _ => ()
        }
      }

      e.getFields.foreach(handleField)
      d.getDomainTypes.foreach { dt =>
        dt.getFields.foreach {
          case l: ListField => {
            if (l.getType == e && dt.isInstanceOf[DataContainer]) {
              dependencyGraph.addEdge(getTableName(e), getTableName(dt.asInstanceOf[DataContainer]))
            }
          }
          case _ => ()
        }
      }
    }
    dependencyGraph.topSort().reverse.map { n =>
      d.getDomainTypes.find { dt =>
        dt.isInstanceOf[DataContainer] && getTableName(dt.asInstanceOf[DataContainer]) == n
      }.asInstanceOf[Option[Entity]].get
    }
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

  private def getTableName(e: DataContainer): String = {
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

  def getColumns(d: Domain)(e: DataContainer): Seq[Column] =
    e.getFields.flatMap {
      case of1@OrdinaryField(name, docs, optional, vo: ValueObject) => {
        vo.getDeclaredFields.flatMap {
          case of2: OrdinaryField => Seq(Column(
            camelCaseToSnakeCase(name) + "_" + camelCaseToSnakeCase(of2.getName),
            sqlType(of2),
            !(optional || of2.isOptional),
            of2.getName == "id",
            None,
            Seq(of1, of2))) // TODO missing constraint
          case _: ListField => Seq()
        }
      }
      case of@OrdinaryField(name, docs, optional, type_) => {
        Seq(Column(camelCaseToSnakeCase(name), sqlType(of), !optional, name == "id", None, Seq(of))) // TODO missing constraint
      }
      case ListField(_, _, _) => Seq()
    } ++ getSubclassFields(d)(e).flatMap {
      case of1@OrdinaryField(name, docs, optional, vo: ValueObject) => {
        vo.getDeclaredFields.flatMap {
          case of2: OrdinaryField => Seq(Column(
            camelCaseToSnakeCase(name) + "_" + camelCaseToSnakeCase(of2.getName),
            sqlType(of2),
            false,
            of2.getName == "id",
            None,
            Seq(of1, of2))) // TODO missing constraint
          case _: ListField => Seq()
        }
      }
      case of@OrdinaryField(name, docs, _, type_) => {
        Seq(Column(
          camelCaseToSnakeCase(name),
          sqlType(of),
          false,
          name == "id",
          None,
          Seq(of))) // TODO missing constraint
      }
      case ListField(_, _, _) => Seq()
    } ++ {
      if (isOnManySideOfListRelation(d)(e)) {
        getOneSideOfListRelation(d)(e) match {
          case Some(column) => Seq(column)
          case None => Seq()
        }
      } else {
        Seq()
      }
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
       |${indent(2)(table.columns.filter(!_.isPrimaryKey).map(_.name).mkString(",\n"))}
       |) VALUES ${(e +: getSubclassesDeep(d)(e)).flatMap(_.getExamples).map(exampleToInsertValuesGroup(d)(table)).map(g => s"(\n  ${g}\n)").mkString(", ")};
       |""".stripMargin
  }

  def getTable(d: Domain)(e: Entity): Table = {
    Table(getTableName(e), getColumns(d)(e))
  }

  def getFieldFromExample(domain: Domain)(example: Example)(fields: Seq[Field]): String = {
    val valueOfHeadField: Value = example.getValueForFieldNamed(domain)(fields.head.getName)
    if (fields.tail.isEmpty) {
      valueOfHeadField match {
        case SimpleValue(value) => s"'${value}'"
        case e: Example => exampleToSelect(e)
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

  def exampleToSelect(example: Example): String = {
    "SELECT id FROM TODO"
  }

  def exampleToInsertValuesGroup(d: Domain)(table: Table)(example: Example): String = {
    table.columns.flatMap(column => {
      if (column.isPrimaryKey) {
        None
      } else {
        if (column.fields.isEmpty) {
          // One side of one-to-many list example
          Some(exampleToSelect(d.exampleContaining(example)))
        } else {
          Some(getFieldFromExample(d)(example)(column.fields))
        }
      }
    }).mkString(",\n  ")
  }
}

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
        case Some(p: DataContainer) => Some(Column(getTableName(p), SqlType("BIGINT"), true, false, foreignKeyConstraint = Option.empty))
        case None => None
      }
    } else {
      None
    }
  }

  def getColumns(d: Domain)(e: DataContainer): Seq[Column] =
    e.getFields.flatMap {
      case OrdinaryField(name, docs, optional, vo: ValueObject) => {
        vo.getDeclaredFields.flatMap {
          case of: OrdinaryField => Seq(Column(
            camelCaseToSnakeCase(name) + "_" + camelCaseToSnakeCase(of.getName),
            sqlType(of),
            !(optional || of.isOptional),
            of.getName == "id",
            None)) // TODO missing constraint
          case _: ListField => Seq()
        }
      }
      case of@OrdinaryField(name, docs, optional, type_) => {
        Seq(Column(camelCaseToSnakeCase(name), sqlType(of), !optional, name == "id", None)) // TODO missing constraint
      }
      case ListField(_, _, _) => Seq()
    } ++ getSubclassFields(d)(e).flatMap {
      case OrdinaryField(name, docs, optional, vo: ValueObject) => {
        vo.getDeclaredFields.flatMap {
          case of: OrdinaryField => Seq(Column(
            camelCaseToSnakeCase(name) + "_" + camelCaseToSnakeCase(of.getName),
            sqlType(of),
            false,
            of.getName == "id",
            None)) // TODO missing constraint
          case _: ListField => Seq()
        }
      }
      case of@OrdinaryField(name, docs, _, type_) => {
        Seq(Column(camelCaseToSnakeCase(name), sqlType(of), false, name == "id", None)) // TODO missing constraint
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

  def exampleToInsertValuesGroup(d: Domain)(table: Table)(example: Example): String = {
    table.columns.flatMap(column => {
      if (column.isPrimaryKey) {
        Seq()
      } else {
        example.fieldValues.find(fv => camelCaseToSnakeCase(fv.getField.getName) == column.name) match {
          case None => Seq("NULL")
          case Some(SimpleFieldValue(field: Field, v: String)) if field.getType == StringType => Seq(s"'${v}'")
          case Some(SimpleFieldValue(field: Field, v: String)) => Seq(v)
          case Some(ReferenceFieldValue(field: Field, ref: String)) => {
            val domainType = field.getType.asInstanceOf[DomainType]
            val example: Example = domainType.getExamples.find(ex => ex.name == ref).get
            domainType match {
              case e: Entity => Seq(s"SELECT id FROM ${getTable(d)(e).name} WHERE TODO")
              case v: ValueObject => example.fieldValues
            }

          }
        }
      }
    }).mkString(",\n  ")
  }
}

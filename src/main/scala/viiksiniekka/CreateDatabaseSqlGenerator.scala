package viiksiniekka

import viiksiniekka.StringUtils.{camelCaseToSnakeCase, indent}
import viiksiniekka.graph.MutableGraph

class CreateDatabaseSqlGenerator extends Generator {
  def generateSum(d: Domain) = {
    val topLevelEntities: Seq[Entity] = d.getDomainTypes.filter { p: DomainType =>
      p match {
        case e: Entity => isTopLevelEntity(d)(e)
        case _ => false
      }
    }.asInstanceOf[Seq[Entity]]
    val topSorted: Seq[Entity] = topSort(d)(topLevelEntities)
    topSorted.map(entitySource(d)).mkString("\n")
  }

  def generateDropTables(d: Domain): String = {
    val topLevelEntities: Seq[Entity] = d.getDomainTypes.filter { p: DomainType =>
      p match {
        case e: Entity => isTopLevelEntity(d)(e)
        case _ => false
      }
    }.asInstanceOf[Seq[Entity]]
    val topSorted: Seq[Entity] = topSort(d)(topLevelEntities)
    val reversed: Seq[Entity] = topSorted.reverse
    reversed.map(dropTableSource(d)).mkString("\n")
  }

  // TODO vpeurala 26.1.2019: Duplicate implementation of topSort? The other is in TopologicalSort class.
  private def topSort(d: Domain)(entities: Seq[Entity]): Seq[Entity] = {
    val dependencyGraph: MutableGraph[String] = new MutableGraph[String]
    val tableNames: Seq[String] = entities.map(_.getTableName)
    tableNames.foreach { t => dependencyGraph.addNode(t) }
    entities.foreach { e =>
      def handleField(f: Field): Unit = {
        f match {
          case l: ListField => ()
          case o: OrdinaryField if o.getType.isInstanceOf[DataContainer] =>
            if (tableNames.contains(o.getType.asInstanceOf[DataContainer].getTableName())) {
              dependencyGraph.addEdge(e.getTableName(), o.getType.asInstanceOf[DataContainer].getTableName())
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
              dependencyGraph.addEdge(e.getTableName(), dt.asInstanceOf[DataContainer].getTableName())
            }
          }
          case _ => ()
        }
      }
    }
    dependencyGraph.topSort().reverse.map { n =>
      d.getDomainTypes.find { dt =>
        dt.isInstanceOf[DataContainer] && dt.asInstanceOf[DataContainer].getTableName() == n
      }.asInstanceOf[Option[Entity]].get
    }
  }

  override def generate(d: Domain): Map[String, String] = {
    val elems = d.getDomainTypes.flatMap {
      case e: DataContainer => if (isTopLevelEntity(d)(e)) {
        Some(sourceFileName(e.getName), entitySource(d)(e))
      } else None
      case _ => None
    }

    Map(elems: _*)
  }

  private def isTopLevelEntity(d: Domain)(ev: DataContainer) = {
    ev match {
      case e: Entity => e.getExtends.isEmpty
      case _: ValueObject => false
    }
  }

  private def sourceFileName(name: String): String = {
    ProjectStructure.SrcMainResources + "/ddl/" + camelCaseToSnakeCase(name) + ".sql"
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

  def embed(domain: Domain)(name: String, optional: Boolean, type_ : Type): String = {
    def notNull(optional_ : Boolean): String = {
      if (optional_) {
        ""
      } else {
        " NOT NULL"
      }
    }

    (name, optional, type_) match {
      case ("id", _, LongType) => "id BIGSERIAL PRIMARY KEY"
      case (name_, optional_, LongType) => s"${camelCaseToSnakeCase(name_)} BIGINT${notNull(optional_)}"
      case (name_, optional_, IntegerType) => s"${camelCaseToSnakeCase(name_)} INT${notNull(optional_)}"
      case (name_, optional_, StringType) => s"${camelCaseToSnakeCase(name_)} VARCHAR${notNull(optional_)}"
      case (name_, optional_, BooleanType) => s"${camelCaseToSnakeCase(name_)} BOOLEAN${notNull(optional_)}"
      case (name_, optional_, LocalTimeType) => s"${camelCaseToSnakeCase(name_)} TIME WITHOUT TIME ZONE${notNull(optional_)}"
      case (name_, optional_, LocalDateTimeType) => s"${camelCaseToSnakeCase(name_)} TIMESTAMP WITHOUT TIME ZONE${notNull(optional_)}"
      case (name_, optional_, e: Enumeration) => s"${camelCaseToSnakeCase(name_)} VARCHAR${notNull(optional_)}"
      case (name_, optional_, e: Entity) => s"${camelCaseToSnakeCase(name_)} BIGINT${notNull(optional_)} REFERENCES ${e.getTableName()}(id)"
      case (name_, optional_, v: ValueObject) => (
        v.getFields.flatMap {
          case of: OrdinaryField => Some(embed(domain)(name_ + "_" + of.getName, optional_ || of.isOptional, of.getType))
          case _: ListField => None
        } ++
          getSubclassFields(domain)(v).flatMap {
            case of: OrdinaryField => Some(embed(domain)(name + "_" + of.getName, optional = true, of.getType))
            case _: ListField => None
          }
        ).mkString(",\n")
    }
  }

  def isOnManySideOfListRelation(d: Domain)(e: DataContainer): Boolean = {
    d.getDomainTypes.exists(p => p.getDeclaredFields.exists {
      case ListField(_, _, type_) => type_ == e
      case _ => false
    })
  }

  def getOneSideOfListRelation(d: Domain)(manySide: DataContainer): String = {
    if (isOnManySideOfListRelation(d)(manySide)) {
      d.getDomainTypes.find(p => p.getDeclaredFields.exists {
        case ListField(_, _, type_) => type_ == manySide
        case _ => false
      }) match {
        case Some(p: DataContainer) => s"${p.getTableName()} BIGINT NOT NULL REFERENCES ${p.getTableName()}(id)"
        case None => ""
      }
    } else {
      ""
    }
  }

  def getColumns(d: Domain)(e: DataContainer): Seq[String] = e.getFields.flatMap {
    case of: OrdinaryField => Some(embed(d)(of.getName, of.isOptional, of.getType))
    case _: ListField => None
  } ++
    getSubclassFields(d)(e).flatMap {
      case of: OrdinaryField => Some(embed(d)(of.getName, optional = true, of.getType))
      case _: ListField => None
    } ++ {
    if (isOnManySideOfListRelation(d)(e)) {
      Seq(getOneSideOfListRelation(d)(e))
    } else {
      Seq()
    }
  }

  def entitySource(d: Domain)(e: DataContainer): String =
    s"""CREATE TABLE ${e.getTableName()} (
       |${
      getColumns(d)(e).map(indent(2)).mkString(",\n")
    }
       |);
       |""".stripMargin

  def dropTableSource(d: Domain)(e: DataContainer): String =
    s"""DROP TABLE IF EXISTS ${e.getTableName()};"""
}

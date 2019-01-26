package viiksiniekka

import viiksiniekka.graph.MutableGraph

object TopologicalSort {
  def topSort(d: Domain)(entities: Seq[Entity]): Seq[Entity] = {
    val dependencyGraph: MutableGraph[String] = new MutableGraph[String]
    val tableNames: Seq[String] = entities.map(_.getTableName())
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
}

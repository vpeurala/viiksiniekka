package viiksiniekka.graph

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, Set}
import scala.collection

class MutableGraph[T] {
  var nodes: mutable.Set[T] = mutable.Set.empty[T]
  var edges: mutable.Set[Edge[T]] = mutable.Set.empty[Edge[T]]

  def addNode(n: T): Unit = {
    nodes += n
  }

  def addEdge(from: T, to: T): Unit = {
    edges += Edge(from, to)
  }

  def topSort(): Seq[T] = {
    // Uses Kahn's algorithm as described in
    // https://en.wikipedia.org/wiki/Topological_sorting
    var edgesTemp: Seq[Edge[T]] = edges.toBuffer
    var result: Seq[T] = ArrayBuffer.empty[T]
    val nodesWithIncomingEdges: scala.collection.Set[T] =
      edgesTemp.map(_.to).toSet
    var nodesWithoutIncomingEdges: Set[T] =
      nodes.filter { n => !nodesWithIncomingEdges.contains(n) }
    while (nodesWithoutIncomingEdges.nonEmpty) {
      val n: T = nodesWithoutIncomingEdges.head
      nodesWithoutIncomingEdges = nodesWithoutIncomingEdges.tail
      result :+= n
      val edgesFromN: Seq[Edge[T]] = edgesTemp.filter(e => e.from == n)
      edgesFromN.foreach { e =>
        val m = e.to
        edgesTemp = edgesTemp.filter(_ != e)
        val edgesToM: Seq[Edge[T]] = edgesTemp.filter(e => e.to == m)
        if (edgesToM.isEmpty) {
          nodesWithoutIncomingEdges += m
        }
      }
    }
    assert(result.size == nodes.size, s"Result size ${result.size} was not equal to nodes size ${nodes.size}.")
    result
  }

  case class Edge[T](from: T, to: T)

}

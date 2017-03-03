package viiksiniekka.graph

import org.scalatest.FunSuite

class GraphTest extends FunSuite {
  test("foo") {
    val g = new MutableGraph[String]
    Seq("Company", "Person", "LogEntry", "ShipArea", "Building", "Ship", "WorkEntry", "Notification").foreach(g.addNode)
    g.addEdge("LogEntry", "Notification")
    g.addEdge("WorkEntry", "Notification")
    g.addEdge("WorkEntry", "ShipArea")
    g.addEdge("WorkEntry", "Ship")
    g.addEdge("WorkEntry", "Building")
    println(g.topSort())
  }
}

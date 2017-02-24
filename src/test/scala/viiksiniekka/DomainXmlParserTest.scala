package viiksiniekka

import org.scalatest.FunSuite

import scala.xml.{Elem, XML}

class DomainXmlParserTest extends FunSuite {
  test("Creates domain") {
    val xml: Elem = XML.load(getClass.getResourceAsStream("/ShipYard.xml"))
    val domain: DomainEl = DomainXmlParser.fromXml(xml)
  }
}

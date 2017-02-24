package viiksiniekka

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.scalatest.FunSuite

import scala.xml.{Elem, XML}

class DomainPrettyPrinterTest extends FunSuite {
  test("Can pretty print domain XML") { // TODO vpeurala 2.2.2017 Finish this test
    val xml: Elem = XML.load(getClass.getResourceAsStream("/ShipYard.xml"))
    val domainEl: DomainEl = DomainXmlParser.fromXml(xml)
    val domain: Domain = DomainXmlTransformer.toDomain(domainEl)
    val pretty: String = DomainPrettyPrinter.pp(domain)
    val expected: String =
      s"""<?xml version="1.0" encoding="UTF-8"?>
         |<domain>
         |</domain>
         |""".stripMargin
    assertEquals(expected, pretty)
  }
}

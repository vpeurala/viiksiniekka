package viiksiniekka

import org.scalatest.FunSuite

import scala.xml.{Elem, XML}

class DomainXmlTransformerTest extends FunSuite {
  test("Creates domain") {
    val xml: Elem = XML.load(getClass.getResourceAsStream("/ShipYard.xml"))
    val domainEl: DomainEl = DomainXmlParser.fromXml(xml)
    val domain: Domain = DomainXmlTransformer.toDomain(domainEl)
    assert(domain.getDomainTypes.length === 22)
  }
}

package viiksiniekka

import org.scalatest.FunSuite

import scala.xml.{Elem, XML}

class DomainXmlParserTest extends FunSuite {
  test("Parses domain") {
    createShipYardDomainEl()
  }

  test("Parses repository operations correctly") {
    val shipYardEl: DomainEl = createShipYardDomainEl()
    val companyRepositoryEl: RepositoryEl = shipYardEl.repositories.find(_.name == "CompanyRepository").get
    assert(companyRepositoryEl.operations.length == 1)
  }

  private def createShipYardDomainEl(): DomainEl = {
    val xml: Elem = XML.load(getClass.getResourceAsStream("/ShipYard.xml"))
    DomainXmlParser.fromXml(xml)
  }
}

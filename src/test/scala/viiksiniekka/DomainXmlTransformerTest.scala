package viiksiniekka

import org.scalatest.FunSuite

import scala.xml.{Elem, XML}

class DomainXmlTransformerTest extends FunSuite {
  test("Creates domain") {
    val shipYard: Domain = createShipYard
    assert(shipYard.getDomainTypes.length === 22)
  }

  test("Creates repository operations correctly") {
    val shipYard: Domain = createShipYard
    val companyRepository: Repository = shipYard.getRepositories.find(_.name == "CompanyRepository").get
    assert(companyRepository.operations.length == 1)
  }

  private def createShipYard: Domain = {
    val xml: Elem = XML.load(getClass.getResourceAsStream("/ShipYard.xml"))
    val domainEl: DomainEl = DomainXmlParser.fromXml(xml)
    DomainXmlTransformer.toDomain(domainEl)
  }
}

package viiksiniekka

import org.scalatest.{BeforeAndAfterAll, FunSuite}

import scala.xml.{Elem, XML}

class JavaRepositoryInterfaceGeneratorTest extends FunSuite with BeforeAndAfterAll with ReadWriteFile {
  var shipYard: Domain = _
  var javaRepositoryInterfaces: Map[String, String] = _

  override def beforeAll(): Unit = {
    shipYard = createShipYard
    javaRepositoryInterfaces = new JavaRepositoryInterfaceGenerator().generate(shipYard)
  }

  test("Can generate CompanyRepository interface correctly") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/repository/CompanyRepository.java")
    val actualSource: String = javaRepositoryInterfaces("src/main/java/com/shipyard/domain/repository/CompanyRepository.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate BuildingRepository interface correctly") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/repository/BuildingRepository.java")
    val actualSource: String = javaRepositoryInterfaces("src/main/java/com/shipyard/domain/repository/BuildingRepository.java")
    assert(expectedSource === actualSource)
  }

  private def createShipYard: Domain = {
    val xml: Elem = XML.load(getClass.getResourceAsStream("/ShipYard.xml"))
    val domainEl: DomainEl = DomainXmlParser.fromXml(xml)
    DomainXmlTransformer.toDomain(domainEl)
  }
}

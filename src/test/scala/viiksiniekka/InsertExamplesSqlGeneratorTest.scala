package viiksiniekka

import org.scalatest.{BeforeAndAfterAll, FunSuite}

import scala.xml.{Elem, XML}

class InsertExamplesSqlGeneratorTest extends FunSuite with BeforeAndAfterAll with ReadWriteFile {
  var shipYard: Domain = _
  var sqlInserts: Map[String, String] = _
  var sqlInsertsSum: String = _

  override def beforeAll(): Unit = {
    shipYard = createShipYard
    val generator = new InsertExamplesSqlGenerator()
    sqlInserts = generator.generate(shipYard)
    sqlInsertsSum = generator.generateSum(shipYard)
  }

  test("Can generate company table test data insertion sql correctly") {
    val expectedSource: String = readFile("src/test/resources/ddl/company_insert.sql")
    val actualSource: String = sqlInserts("src/main/resources/ddl/company_insert.sql")
    writeFile("src/test/resources/ddl/company_insert_actual.sql", actualSource)
    assert(expectedSource === actualSource)
  }

  private def createShipYard: Domain = {
    val xml: Elem = XML.load(getClass.getResourceAsStream("/ShipYard.xml"))
    val domainEl: DomainEl = DomainXmlParser.fromXml(xml)
    DomainXmlTransformer.toDomain(domainEl)
  }
}
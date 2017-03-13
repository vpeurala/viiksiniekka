package viiksiniekka

import org.scalatest.{BeforeAndAfterAll, FunSuite}

import scala.xml.{Elem, XML}

class InsertsSqlGeneratorTest extends FunSuite with BeforeAndAfterAll with ReadWriteFile {
  var shipYard: Domain = _
  var sqlTableCreationStatements: Map[String, String] = _
  var sqlTableCreationStatementsSum: String = _
  var sqlInserts: Map[String, String] = _
  var sqlInsertsSum: String = _

  override def beforeAll(): Unit = {
    shipYard = createShipYard
    val generator = new CreateDatabaseSqlGenerator()
    sqlTableCreationStatements = generator.generate(shipYard)
    sqlTableCreationStatementsSum = generator.generateSum(shipYard)
    sqlInserts = generator.generate(shipYard)
    sqlInsertsSum = generator.generateSum(shipYard)
  }

  test("Can generate person table test data insertion sql correctly") {
    val expectedSource: String = readFile("src/test/resources/ddl/person_insert.sql")
    val actualSource: String = sqlTableCreationStatements("src/main/resources/ddl/person_insert.sql")
    assert(expectedSource === actualSource)
  }

  private def createShipYard: Domain = {
    val xml: Elem = XML.load(getClass.getResourceAsStream("/ShipYard.xml"))
    val domainEl: DomainEl = DomainXmlParser.fromXml(xml)
    DomainXmlTransformer.toDomain(domainEl)
  }
}

package viiksiniekka

import org.scalatest.{BeforeAndAfterAll, FunSuite}

import scala.xml.{Elem, XML}

class SqlGeneratorTest extends FunSuite with BeforeAndAfterAll with ReadWriteFile {
  var shipYard: Domain = _
  var sqlTableCreationStatements: Map[String, String] = _
  var sqlTableCreationStatementsSum: String = _

  override def beforeAll(): Unit = {
    shipYard = createShipYard
    val generator = new CreateDatabaseSqlGenerator()
    sqlTableCreationStatements = generator.generate(shipYard)
    sqlTableCreationStatementsSum = generator.generateSum(shipYard)
  }

  test("Can generate person table creation sql correctly") {
    val expectedSource: String = readFile("src/test/resources/ddl/person.sql")
    val actualSource: String = sqlTableCreationStatements("src/main/resources/ddl/person.sql")
    assert(expectedSource === actualSource)
  }

  test("Can generate notification table creation sql correctly") {
    val expectedSource: String = readFile("src/test/resources/ddl/notification.sql")
    val actualSource: String = sqlTableCreationStatements("src/main/resources/ddl/notification.sql")
    assert(expectedSource === actualSource)
  }

  test("Can generate work_entry table creation sql correctly") {
    val expectedSource: String = readFile("src/test/resources/ddl/work_entry.sql")
    val actualSource: String = sqlTableCreationStatements("src/main/resources/ddl/work_entry.sql")
    assert(expectedSource === actualSource)
  }

  test("Can generate log_entry table creation sql correctly") {
    val expectedSource: String = readFile("src/test/resources/ddl/log_entry.sql")
    val actualSource: String = sqlTableCreationStatements("src/main/resources/ddl/log_entry.sql")
    assert(expectedSource === actualSource)
  }

  test("Can generate company table creation sql correctly") {
    val expectedSource: String = readFile("src/test/resources/ddl/company.sql")
    val actualSource: String = sqlTableCreationStatements("src/main/resources/ddl/company.sql")
    assert(expectedSource === actualSource)
  }

  test("Can generate building table creation sql correctly") {
    val expectedSource: String = readFile("src/test/resources/ddl/building.sql")
    val actualSource: String = sqlTableCreationStatements("src/main/resources/ddl/building.sql")
    assert(expectedSource === actualSource)
  }

  test("Can generate ship table creation sql correctly") {
    val expectedSource: String = readFile("src/test/resources/ddl/ship.sql")
    val actualSource: String = sqlTableCreationStatements("src/main/resources/ddl/ship.sql")
    assert(expectedSource === actualSource)
  }

  test("Can generate ship_area table creation sql correctly") {
    val expectedSource: String = readFile("src/test/resources/ddl/ship_area.sql")
    val actualSource: String = sqlTableCreationStatements("src/main/resources/ddl/ship_area.sql")
    assert(expectedSource === actualSource)
  }

  test("Can generate sum of table creation sqls correctly in topologically sorted order") {
    val expectedSource: String = readFile("src/test/resources/ddl/V1__Create_Tables_ShipYard.sql")
    val actualSource: String = sqlTableCreationStatementsSum
    assert(expectedSource === actualSource)
  }

  private def createShipYard: Domain = {
    val xml: Elem = XML.load(getClass.getResourceAsStream("/ShipYard.xml"))
    val domainEl: DomainEl = DomainXmlParser.fromXml(xml)
    DomainXmlTransformer.toDomain(domainEl)
  }
}

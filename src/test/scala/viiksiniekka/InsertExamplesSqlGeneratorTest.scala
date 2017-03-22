package viiksiniekka

import org.scalatest.{BeforeAndAfterAll, FunSuite}

import scala.xml.{Elem, XML}

class InsertExamplesSqlGeneratorTest extends FunSuite with BeforeAndAfterAll with ReadWriteFile {
  var shipYard: Domain = _
  var generator: InsertExamplesSqlGenerator = _
  var sqlInserts: Map[String, String] = _
  var sqlInsertsSum: String = _

  override def beforeAll(): Unit = {
    shipYard = createShipYard
    generator = new InsertExamplesSqlGenerator()
    sqlInserts = generator.generate(shipYard)
    sqlInsertsSum = generator.generateSum(shipYard)
  }

  test("Can generate company table test data insertion sql correctly") {
    val expectedSource: String = readFile("src/test/resources/ddl/company_insert.sql")
    val actualSource: String = sqlInserts("src/main/resources/ddl/company_insert.sql")
    assert(expectedSource === actualSource)
  }

  test("Can generate building table test data insertion sql correctly") {
    val expectedSource: String = readFile("src/test/resources/ddl/building_insert.sql")
    val actualSource: String = sqlInserts("src/main/resources/ddl/building_insert.sql")
    assert(expectedSource === actualSource)
  }

  test("Extracts the correct columns for person table") {
    val columns: Seq[Column] = generator.getColumns(shipYard)(shipYard.entityForName("Person"))
    assert(columns.size === 9)
    assert(columns(0) === Column("id", SqlType("BIGINT"), notNull = true, isPrimaryKey = true, None))
    assert(columns(1) === Column("first_name", SqlType("VARCHAR"), notNull = true, isPrimaryKey = false, None))
    assert(columns(2) === Column("last_name", SqlType("VARCHAR"), notNull = true, isPrimaryKey = false, None))
    assert(columns(3) === Column("company", SqlType("BIGINT"), notNull = false, isPrimaryKey = false, None)) // TODO missing constraint
    assert(columns(4) === Column("contact_information_email", SqlType("VARCHAR"), notNull = false, isPrimaryKey = false, None))
  }

  test("Can generate person table test data insertion sql correctly") {
    //val expectedSource: String = readFile("src/test/resources/ddl/person_insert.sql")
    val actualSource: String = sqlInserts("src/main/resources/ddl/person_insert.sql")
    writeFile("src/test/resources/ddl/person_insert.sql", actualSource)
  }

  private def createShipYard: Domain = {
    val xml: Elem = XML.load(getClass.getResourceAsStream("/ShipYard.xml"))
    val domainEl: DomainEl = DomainXmlParser.fromXml(xml)
    DomainXmlTransformer.toDomain(domainEl)
  }
}

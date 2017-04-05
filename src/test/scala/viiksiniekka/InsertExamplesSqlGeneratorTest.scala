package viiksiniekka

import org.scalatest.{BeforeAndAfterAll, FunSuite}

import scala.xml.{Elem, XML}

class InsertExamplesSqlGeneratorTest extends FunSuite with BeforeAndAfterAll with ReadWriteFile {
  var shipYard: Domain = _
  var generator: InsertExamplesSqlGenerator = _

  override def beforeAll(): Unit = {
    shipYard = createShipYard
    generator = new InsertExamplesSqlGenerator()
  }

  test("Can generate company table test data insertion sql correctly") {
    val sqlInserts = generator.generate(shipYard)
    val expectedSource: String = readFile("src/test/resources/ddl/company_insert.sql")
    val actualSource: String = sqlInserts("src/main/resources/ddl/company_insert.sql")
    assert(expectedSource === actualSource)
  }

  test("Can generate building table test data insertion sql correctly") {
    val sqlInserts = generator.generate(shipYard)
    val expectedSource: String = readFile("src/test/resources/ddl/building_insert.sql")
    val actualSource: String = sqlInserts("src/main/resources/ddl/building_insert.sql")
    assert(expectedSource === actualSource)
  }

  test("Extracts the correct columns for person table") {
    val columns: Seq[Column] = generator.getColumns(shipYard)(shipYard.entityForName("Person"))
    assert(columns.size === 10)
    val idColumn: Column = columns(0)
    assert(idColumn.name === "id")
    assert(idColumn.sqlType === SqlType("BIGINT"))
    val firstNameColumn: Column = columns(1)
    assert(firstNameColumn.name === "first_name")
    val lastNameColumn: Column = columns(2)
    assert(lastNameColumn.name === "last_name")
    val companyColumn: Column = columns(3)
    assert(companyColumn.name === "company")
    val contactInformationEmailColumn = columns(4)
    assert(contactInformationEmailColumn.name === "contact_information_email")
    val contactInformationPhoneNumberColumn = columns(5)
    assert(contactInformationPhoneNumberColumn.name === "contact_information_phone_number")
    val confirmedColumn = columns(6)
    assert(confirmedColumn.name === "confirmed")
    val tokenColumn = columns(7)
    assert(tokenColumn.name === "token")
    val passwordColumn = columns(8)
    assert(passwordColumn.name === "password")
    val keyCodeColumn = columns(9)
    assert(keyCodeColumn.name === "key_code")
  }

  test("Can get example containing another example") {
    val creationOfNotification1 = shipYard.exampleForName("Creation of notification 1")
    val exampleContainingCreationOfNotification1 = shipYard.exampleContaining(creationOfNotification1)
    assert(exampleContainingCreationOfNotification1 === shipYard.exampleForName("Notification 1"))
  }

  test("Can generate person table test data insertion sql correctly") {
    val sqlInserts = generator.generate(shipYard)
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

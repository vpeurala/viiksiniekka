package viiksiniekka

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.scalatest.{BeforeAndAfterAll, FunSuite}

import scala.xml.{Elem, XML}

class JavaGeneratorTest extends FunSuite with BeforeAndAfterAll with ReadWriteFile {
  var shipYard: Domain = _
  var javaObjects: Map[String, String] = _
  var javaBuilders: Map[String, String] = _
  var javaExamples: Map[String, String] = _

  override def beforeAll(): Unit = {
    shipYard = createShipYard
    javaObjects = new JavaDataGenerator().generate(shipYard)
    javaBuilders = new JavaBuilderGenerator().generate(shipYard)
    javaExamples = new JavaExamplesGenerator().generate(shipYard)
  }

  test("Can generate Person class correctly") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/data/Person.java")
    val actualSource: String = javaObjects("src/main/java/com/shipyard/domain/data/Person.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate Company class correctly") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/data/Company.java")
    val actualSource: String = javaObjects("src/main/java/com/shipyard/domain/data/Company.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate EmployedPerson class correctly") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/data/EmployedPerson.java")
    val actualSource: String = javaObjects("src/main/java/com/shipyard/domain/data/EmployedPerson.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate Building class correctly") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/data/Building.java")
    val actualSource: String = javaObjects("src/main/java/com/shipyard/domain/data/Building.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate Ship class correctly") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/data/Ship.java")
    val actualSource: String = javaObjects("src/main/java/com/shipyard/domain/data/Ship.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate ShipArea class correctly") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/data/ShipArea.java")
    val actualSource: String = javaObjects("src/main/java/com/shipyard/domain/data/ShipArea.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate Location class correctly") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/data/Location.java")
    val actualSource: String = javaObjects("src/main/java/com/shipyard/domain/data/Location.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate Worker class correctly") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/data/Worker.java")
    val actualSource: String = javaObjects("src/main/java/com/shipyard/domain/data/Worker.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate ContactInformation class correctly") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/data/ContactInformation.java")
    val actualSource: String = javaObjects("src/main/java/com/shipyard/domain/data/ContactInformation.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate ContactPerson class correctly") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/data/ContactPerson.java")
    val actualSource: String = javaObjects("src/main/java/com/shipyard/domain/data/ContactPerson.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate TimeOfDay class correctly") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/data/TimeOfDay.java")
    val actualSource: String = javaObjects("src/main/java/com/shipyard/domain/data/TimeOfDay.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate Workday class correctly") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/data/Workday.java")
    val actualSource: String = javaObjects("src/main/java/com/shipyard/domain/data/Workday.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate Workweek class correctly") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/data/Workweek.java")
    val actualSource: String = javaObjects("src/main/java/com/shipyard/domain/data/Workweek.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate EnergyRequirements class correctly") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/data/EnergyRequirements.java")
    val actualSource: String = javaObjects("src/main/java/com/shipyard/domain/data/EnergyRequirements.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate Occupation class correctly") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/data/Occupation.java")
    val actualSource: String = javaObjects("src/main/java/com/shipyard/domain/data/Occupation.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate WorkEntry class correctly") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/data/WorkEntry.java")
    val actualSource: String = javaObjects("src/main/java/com/shipyard/domain/data/WorkEntry.java")
    Assert.assertEquals("expected: \"" + expectedSource + "\" but was: \"" + actualSource + "\"", expectedSource, actualSource)
  }

  test("Can generate NotificationAction class correctly") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/data/NotificationAction.java")
    val actualSource: String = javaObjects("src/main/java/com/shipyard/domain/data/NotificationAction.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate User class correctly") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/data/User.java")
    val actualSource: String = javaObjects("src/main/java/com/shipyard/domain/data/User.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate LogEntry class correctly") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/data/LogEntry.java")
    val actualSource: String = javaObjects("src/main/java/com/shipyard/domain/data/LogEntry.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate NotificationStatus class correctly") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/data/NotificationStatus.java")
    val actualSource: String = javaObjects("src/main/java/com/shipyard/domain/data/NotificationStatus.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate Notification class correctly") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/data/Notification.java")
    val actualSource: String = javaObjects("src/main/java/com/shipyard/domain/data/Notification.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate Builder for Person") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/builder/PersonBuilder.java")
    val actualSource: String = javaBuilders("src/main/java/com/shipyard/domain/builder/PersonBuilder.java")
    assertEquals(expectedSource, actualSource)
  }

  test("Can generate Builder for ContactInformation") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/builder/ContactInformationBuilder.java")
    val actualSource: String = javaBuilders("src/main/java/com/shipyard/domain/builder/ContactInformationBuilder.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate Builder for Company") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/builder/CompanyBuilder.java")
    val actualSource: String = javaBuilders("src/main/java/com/shipyard/domain/builder/CompanyBuilder.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate Builder for ContactPerson") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/builder/ContactPersonBuilder.java")
    val actualSource: String = javaBuilders("src/main/java/com/shipyard/domain/builder/ContactPersonBuilder.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate Builder for Building") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/builder/BuildingBuilder.java")
    val actualSource: String = javaBuilders("src/main/java/com/shipyard/domain/builder/BuildingBuilder.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate Builder for Ship") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/builder/ShipBuilder.java")
    val actualSource: String = javaBuilders("src/main/java/com/shipyard/domain/builder/ShipBuilder.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate Builder for ShipArea") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/builder/ShipAreaBuilder.java")
    val actualSource: String = javaBuilders("src/main/java/com/shipyard/domain/builder/ShipAreaBuilder.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate Builder for Location") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/builder/LocationBuilder.java")
    val actualSource: String = javaBuilders("src/main/java/com/shipyard/domain/builder/LocationBuilder.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate Builder for Worker") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/builder/WorkerBuilder.java")
    val actualSource: String = javaBuilders("src/main/java/com/shipyard/domain/builder/WorkerBuilder.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate Builder for TimeOfDay") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/builder/TimeOfDayBuilder.java")
    val actualSource: String = javaBuilders("src/main/java/com/shipyard/domain/builder/TimeOfDayBuilder.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate Builder for Workday") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/builder/WorkdayBuilder.java")
    val actualSource: String = javaBuilders("src/main/java/com/shipyard/domain/builder/WorkdayBuilder.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate Builder for Workweek") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/builder/WorkweekBuilder.java")
    val actualSource: String = javaBuilders("src/main/java/com/shipyard/domain/builder/WorkweekBuilder.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate Builder for EnergyRequirements") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/builder/EnergyRequirementsBuilder.java")
    val actualSource: String = javaBuilders("src/main/java/com/shipyard/domain/builder/EnergyRequirementsBuilder.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate Builder for WorkEntry") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/builder/WorkEntryBuilder.java")
    val actualSource: String = javaBuilders("src/main/java/com/shipyard/domain/builder/WorkEntryBuilder.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate Builder for User") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/builder/UserBuilder.java")
    val actualSource: String = javaBuilders("src/main/java/com/shipyard/domain/builder/UserBuilder.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate Builder for LogEntry") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/builder/LogEntryBuilder.java")
    val actualSource: String = javaBuilders("src/main/java/com/shipyard/domain/builder/LogEntryBuilder.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate Builder for Notification") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/builder/NotificationBuilder.java")
    val actualSource: String = javaBuilders("src/main/java/com/shipyard/domain/builder/NotificationBuilder.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate examples for Person") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/example/PersonExamples.java")
    val actualSource: String = javaExamples("src/test/java/com/shipyard/domain/example/PersonExamples.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate examples for Building") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/example/BuildingExamples.java")
    val actualSource: String = javaExamples("src/test/java/com/shipyard/domain/example/BuildingExamples.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate examples for Ship") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/example/ShipExamples.java")
    val actualSource: String = javaExamples("src/test/java/com/shipyard/domain/example/ShipExamples.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate examples for ShipArea") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/example/ShipAreaExamples.java")
    val actualSource: String = javaExamples("src/test/java/com/shipyard/domain/example/ShipAreaExamples.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate examples for Location") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/example/LocationExamples.java")
    val actualSource: String = javaExamples("src/test/java/com/shipyard/domain/example/LocationExamples.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate examples for Worker") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/example/WorkerExamples.java")
    val actualSource: String = javaExamples("src/test/java/com/shipyard/domain/example/WorkerExamples.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate examples for ContactInformation") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/example/ContactInformationExamples.java")
    val actualSource: String = javaExamples("src/test/java/com/shipyard/domain/example/ContactInformationExamples.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate examples for ContactPerson") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/example/ContactPersonExamples.java")
    val actualSource: String = javaExamples("src/test/java/com/shipyard/domain/example/ContactPersonExamples.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate examples for TimeOfDay") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/example/TimeOfDayExamples.java")
    val actualSource: String = javaExamples("src/test/java/com/shipyard/domain/example/TimeOfDayExamples.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate examples for Workday") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/example/WorkdayExamples.java")
    val actualSource: String = javaExamples("src/test/java/com/shipyard/domain/example/WorkdayExamples.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate examples for Workweek") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/example/WorkweekExamples.java")
    val actualSource: String = javaExamples("src/test/java/com/shipyard/domain/example/WorkweekExamples.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate examples for EnergyRequirements") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/example/EnergyRequirementsExamples.java")
    val actualSource: String = javaExamples("src/test/java/com/shipyard/domain/example/EnergyRequirementsExamples.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate examples for WorkEntry") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/example/WorkEntryExamples.java")
    val actualSource: String = javaExamples("src/test/java/com/shipyard/domain/example/WorkEntryExamples.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate examples for Notification") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/example/NotificationExamples.java")
    val actualSource: String = javaExamples("src/test/java/com/shipyard/domain/example/NotificationExamples.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate examples for Company") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/example/CompanyExamples.java")
    val actualSource: String = javaExamples("src/test/java/com/shipyard/domain/example/CompanyExamples.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate examples for User") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/example/UserExamples.java")
    val actualSource: String = javaExamples("src/test/java/com/shipyard/domain/example/UserExamples.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate examples for LogEntry") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/example/LogEntryExamples.java")
    val actualSource: String = javaExamples("src/test/java/com/shipyard/domain/example/LogEntryExamples.java")
    assert(expectedSource === actualSource)
  }

  test("Can generate aggregate NewNotification") {
    val expectedLocationSource: String = readFile("src/test/java/com/shipyard/domain/data/NewLocation.java")
    val actualLocationSource: String = javaObjects("src/main/java/com/shipyard/domain/data/NewLocation.java")
    assert(expectedLocationSource == actualLocationSource)

    val expectedWorkEntrySource: String = readFile("src/test/java/com/shipyard/domain/data/NewWorkEntry.java")
    val actualWorkEntrySource: String = javaObjects("src/main/java/com/shipyard/domain/data/NewWorkEntry.java")
    assert(expectedWorkEntrySource == actualWorkEntrySource)

    val expectedNotificationSource: String = readFile("src/test/java/com/shipyard/domain/data/NewNotification.java")
    val actualNotificationSource: String = javaObjects("src/main/java/com/shipyard/domain/data/NewNotification.java")
    assert(expectedNotificationSource == actualNotificationSource)
  }

  test("Can generate builders for aggregate NewNotification") {
    val expectedLocationSource: String = readFile("src/test/java/com/shipyard/domain/builder/NewLocationBuilder.java")
    val actualLocationSource: String = javaBuilders("src/main/java/com/shipyard/domain/builder/NewLocationBuilder.java")
    assert(expectedLocationSource == actualLocationSource)

    val expectedWorkEntrySource: String = readFile("src/test/java/com/shipyard/domain/builder/NewWorkEntryBuilder.java")
    val actualWorkEntrySource: String = javaBuilders("src/main/java/com/shipyard/domain/builder/NewWorkEntryBuilder.java")
    assert(expectedWorkEntrySource == actualWorkEntrySource)

    val expectedNotificationSource: String = readFile("src/test/java/com/shipyard/domain/builder/NewNotificationBuilder.java")
    val actualNotificationSource: String = javaBuilders("src/main/java/com/shipyard/domain/builder/NewNotificationBuilder.java")
    assert(expectedNotificationSource == actualNotificationSource)
  }

  test("Can generate visitor for enumeration NotificationStatus") {
    val expectedSource: String = readFile("src/test/java/com/shipyard/domain/visitor/NotificationStatusVisitor.java")
    val actualSource: String = javaObjects("src/main/java/com/shipyard/domain/visitor/NotificationStatusVisitor.java")
    assert(expectedSource == actualSource)
  }

  private def createShipYard: Domain = {
    val xml: Elem = XML.load(getClass.getResourceAsStream("/ShipYard.xml"))
    val domainEl: DomainEl = DomainXmlParser.fromXml(xml)
    DomainXmlTransformer.toDomain(domainEl)
  }
}

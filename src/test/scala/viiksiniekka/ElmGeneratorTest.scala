package viiksiniekka

import org.scalatest.{BeforeAndAfterAll, FunSuite}

import scala.xml.{Elem, XML}

class ElmGeneratorTest extends FunSuite with BeforeAndAfterAll with ReadWriteFile {
  var shipYard: Domain = _
  var elmFiles: Map[String, String] = _

  override def beforeAll(): Unit = {
    shipYard = createShipYard
    elmFiles = new ElmDataGenerator().generate(shipYard) ++
      new ElmDecoderGenerator().generate(shipYard) ++
      new ElmEncoderGenerator().generate(shipYard)
  }

  test("Can generate Domain.elm correctly") {
    val expectedSource: String = readFile("src/test/elm/Domain.elm")
    val actualSource: String = elmFiles("src/main/elm/Domain.elm")
    assert(expectedSource === actualSource)
  }

  test("Can generate Decoders.elm correctly") {
    val expectedSource: String = readFile("src/test/elm/Decoders.elm")
    val actualSource: String = elmFiles("src/main/elm/Decoders.elm")
    assert(expectedSource === actualSource)
  }

  test("Can generate Encoders.elm correctly") {
    val expectedSource: String = readFile("src/test/elm/Encoders.elm")
    val actualSource: String = elmFiles("src/main/elm/Encoders.elm")
    assert(expectedSource === actualSource)
  }

  private def createShipYard: Domain = {
    val xml: Elem = XML.load(getClass.getResourceAsStream("/ShipYard.xml"))
    val domainEl: DomainEl = DomainXmlParser.fromXml(xml)
    DomainXmlTransformer.toDomain(domainEl)
  }
}

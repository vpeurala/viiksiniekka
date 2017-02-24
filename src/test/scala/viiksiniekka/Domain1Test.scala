package viiksiniekka

import viiksiniekka.extensions._
import org.scalatest.FunSuite

class Domain1Test extends FunSuite {
  test("First smoke test") {
    val domain = domain1
    assert(domain.declarations.length === 16)
    val entities = domain.declarations.filter(_.isInstanceOf[EntityDeclaration])
    assert(entities.length === 11)
    val typeAliases = domain.declarations.filter(_.isInstanceOf[TypeAliasDeclaration])
    assert(typeAliases.length === 2)
    val sumTypes = domain.declarations.filter(_.isInstanceOf[SumTypeDeclaration])
    assert(sumTypes.length === 3)
  }

  def domain1: DatamodelDeclaration = CustomFormatParser.parseFile("src/test/resources/Domain1.dom")
}

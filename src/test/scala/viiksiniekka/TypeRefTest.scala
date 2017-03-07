package viiksiniekka

import org.scalatest.FunSuite

class TypeRefTest extends FunSuite {
  test("String is a simple type") {
    val tr = TypeRef.fromString("String")
    assert(!tr.isList)
    assert(tr.isSimple)
    assert(tr.baseTypeName == "String")
  }

  test("List String is a list type") {
    val tr = TypeRef.fromString("List String")
    assert(tr.isList)
    assert(!tr.isSimple)
    assert(tr.baseTypeName == "String")
  }
}

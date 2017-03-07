package viiksiniekka

sealed trait TypeRef {
  def isSimple: Boolean

  def isList: Boolean

  def baseTypeName: String
}

class SimpleTypeRef(val baseTypeName: String) extends TypeRef {
  override def isSimple: Boolean = true

  override def isList: Boolean = false
}

class ListTypeRef(val baseTypeName: String) extends TypeRef {
  override def isSimple: Boolean = false

  override def isList: Boolean = true
}

object TypeRef {
  def fromString(input: String): TypeRef = {
    if (input.startsWith("List")) {
      new ListTypeRef(input.substring("List ".length))
    } else {
      new SimpleTypeRef(input)
    }
  }
}

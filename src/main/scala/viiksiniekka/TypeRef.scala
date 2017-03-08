package viiksiniekka

sealed trait TypeRef {
  def isSimple: Boolean

  def isList: Boolean

  def baseTypeName: String
}

case class SimpleTypeRef(baseTypeName: String) extends TypeRef {
  override def isSimple: Boolean = true

  override def isList: Boolean = false
}

case class ListTypeRef(baseTypeName: String) extends TypeRef {
  override def isSimple: Boolean = false

  override def isList: Boolean = true
}

object TypeRef {
  def fromString(input: String): TypeRef = {
    if (input.startsWith("List")) {
      ListTypeRef(input.substring("List ".length))
    } else {
      SimpleTypeRef(input)
    }
  }
}

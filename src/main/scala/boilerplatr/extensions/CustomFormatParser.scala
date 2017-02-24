package boilerplatr.extensions

import boilerplatr.extensions.DependencyStyle.DependencyStyle
import org.parboiled2._
import shapeless.{::, HNil}

import scala.collection.mutable.ArrayBuffer
import scala.io.{BufferedSource, Source}
import scala.language.implicitConversions
import scala.util.{Failure, Success, Try}

case class DatamodelDeclaration(declarations: Seq[TopLevelDeclaration]) {
  def entities: Seq[EntityDeclaration] = {
    declarations.filter {
      case EntityDeclaration(name, fields) => true
      case _ => false
    }.asInstanceOf[Seq[EntityDeclaration]]
  }

  def incoming(n: TopLevelDeclaration): Seq[TopLevelDeclaration] = {
    declarations.filter { d => d.outgoingDeclarations.exists { od => od.contains(n.name) } }
  }

  def topSort(dependencyStyle: DependencyStyle): Seq[TopLevelDeclaration] = {
    var sortedElements = ArrayBuffer.empty[TopLevelDeclaration]
    var edges = ArrayBuffer.empty[MutableEdge]
    declarations.foreach { t =>
      t.outgoingDeclarations.foreach { o =>
        if (!Seq("String", "Boolean").contains(o)) {
          edges.append(MutableEdge(t.name, o))
        }
      }
    }
    var nodesWithoutIncomingEdges = declarations.filter { t =>
      edges.forall { e =>
        e.to != t.name
      }
    }
    while (nodesWithoutIncomingEdges.nonEmpty) {
      val removedElement = nodesWithoutIncomingEdges.head
      nodesWithoutIncomingEdges = nodesWithoutIncomingEdges.tail
      sortedElements.append(removedElement)
      edges = edges.filter { e =>
        e.from != removedElement.name && e.to != removedElement.name
      }
      nodesWithoutIncomingEdges ++= declarations.filter { t =>
        !sortedElements.contains(t) &&
          !nodesWithoutIncomingEdges.contains(t) &&
          edges.forall { e =>
            e.to != t.name
          }
      }.distinct
    }
    sortedElements
  }
}

object DependencyStyle extends Enumeration {
  type DependencyStyle = Value
  val AlwaysFromNSide, CanBeFromEitherSide = Value
}

case class MutableEdge(from: String, to: String)

sealed trait TopLevelDeclaration {
  def name: String

  def outgoingDeclarations: Seq[String]
}

case class EntityDeclaration(name: String, fields: Seq[FieldDeclaration]) extends TopLevelDeclaration {
  override def outgoingDeclarations: Seq[String] = {
    fields.flatMap(_.fieldType.split(' ')).distinct
  }
}

case class TypeAliasDeclaration(name: String, value: String) extends TopLevelDeclaration {
  override def outgoingDeclarations: Seq[String] = {
    value.split(' ').distinct
  }
}

case class NewTypeDeclaration(name: String, value: String) extends TopLevelDeclaration {
  override def outgoingDeclarations: Seq[String] = {
    value.split(' ').distinct
  }
}

case class SumTypeDeclaration(name: String, constructors: Seq[ConstructorDeclaration]) extends TopLevelDeclaration {
  override def outgoingDeclarations: Seq[String] = {
    constructors.flatMap(_.args).flatten.distinct
  }
}

sealed trait FieldOrConstructor {
  def name: String

  def fieldOrConstructor: FieldOrConstructor
}

case class FieldDeclaration(name: String, fieldType: String) extends FieldOrConstructor {
  override def fieldOrConstructor: FieldOrConstructor = this
}

case class ConstructorDeclaration(name: String, args: Option[Seq[String]]) extends FieldOrConstructor {
  override def fieldOrConstructor: FieldOrConstructor = this
}

class DatamodelParser(val input: ParserInput) extends Parser with StringBuilding {
  implicit def wspString(s: String): Rule0 = rule {
    str(s) ~ WhiteSpace
  }

  def Constructor: Rule1[ConstructorDeclaration] = rule {
    Identifier ~ optional(ConstructorArgs) ~> ConstructorDeclaration
  }

  def ConstructorArgs: Rule1[Seq[String]] = rule {
    " " ~ zeroOrMore(Identifier).separatedBy(" ")
  }

  def Data: Rule1[SumTypeDeclaration] = rule {
    "data" ~ Identifier ~ " =" ~ oneOrMore(Constructor).separatedBy("\n" ~ "|") ~> SumTypeDeclaration
  }

  def Datamodel: Rule1[DatamodelDeclaration] = rule {
    oneOrMore(TopLevelDeclaration).separatedBy("\n\n") ~> DatamodelDeclaration
  }

  def Entity: Rule1[EntityDeclaration] = rule {
    "entity" ~ Identifier ~ "\n  " ~ oneOrMore(Field).separatedBy("\n") ~> EntityDeclaration
  }

  def Field: Rule1[FieldDeclaration] = rule {
    Identifier ~ ":" ~ FieldType ~> FieldDeclaration
  }

  def FieldType: Rule1[String] = rule {
    clearSB() ~ oneOrMore(noneOf("\n") ~ appendSB) ~ push(sb.toString())
  }

  def Identifier: Rule1[String] = rule {
    clearSB() ~ oneOrMore(CharPredicate.AlphaNum ~ appendSB) ~ push(sb.toString())
  }

  def Input: Rule[HNil, ::[DatamodelDeclaration, HNil]] = rule {
    WhiteSpace ~ Datamodel ~ WhiteSpace ~ EOI
  }

  def TopLevelDeclaration: Rule1[TopLevelDeclaration] = rule {
    (Entity | Type | NewType | Data)
  }

  def Type: Rule1[TypeAliasDeclaration] = rule {
    "type" ~ Identifier ~ " =" ~ Identifier ~> TypeAliasDeclaration
  }

  def NewType: Rule1[NewTypeDeclaration] = rule {
    "newtype" ~ Identifier ~ " =" ~ Identifier ~> NewTypeDeclaration
  }

  def WhiteSpace = rule {
    zeroOrMore(WhiteSpaceChar)
  }

  def WhiteSpaceChar = CharPredicate(" \n\r\t\f")
}

object CustomFormatParser {
  def main(args: Array[String]): Unit = {
    val filename = "src/test/resources/Domain1.dom"
    val ast = parseFile(filename)
    generateDomainClasses(ast)
  }

  def parseFile(filename: String): DatamodelDeclaration = {
    val source: BufferedSource = Source.fromFile(filename)
    val code: String = source.mkString
    val parser = new DatamodelParser(code)
    val result: Try[DatamodelDeclaration] = parser.Input.run()
    result match {
      case Success(a) => a
      case Failure(e: ParseError) => println(parser.formatError(e, new ErrorFormatter(true, true, true, true, true, 2, 1000))); throw e
      case Failure(e) => throw e
    }
  }

  def generateDomainClasses(ast: DatamodelDeclaration) = {
    ast.topSort(DependencyStyle.CanBeFromEitherSide).foreach {
      t => println(write(t))
    }

    def write(t: TopLevelDeclaration) = {
      t match {
        case EntityDeclaration(name, fields) => {
          s"""public class ${name} {${
            fields.map { f =>
              "  private final ${f.fieldType} ${f.name}"
            }
          }}}
            """.stripMargin
        }
        case _ => ""
      }
    }
  }
}



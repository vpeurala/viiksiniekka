package viiksiniekka

import scala.language.implicitConversions

trait ElmGenerator extends Generator {
  implicit def domainType2ElmType(dt: DomainType): ElmType = {
    new ElmType(dt)
  }

  class ElmType(val dt: DomainType) {
    def getElmFields: Seq[ElmField] = {
      dt match {
        case d: DataContainer if d.getExtends.isDefined => {
          val superClass: DomainType = d.getExtends.get
          Seq(ElmField(
            StringUtils.downcaseFirst(superClass.getName),
            superClass.getName,
            StringUtils.downcaseFirst(superClass.getName),
            s"${StringUtils.downcaseFirst(superClass.getName)} o.${StringUtils.downcaseFirst(superClass.getName)}")) ++ d.getDeclaredFields.map(toElmField)
        }
        case d: DataContainer => {
          d.getDeclaredFields.map(toElmField)
        }
        case _ => Seq[ElmField]() // TODO vpeurala implement
      }
    }
  }

  def toElmField(f: Field): ElmField = {
    f match {
      case f: ListField => ElmField(f.getName, "List " + asElmType(f.getType), "(list " + decoder(f.getType) + ")", s"list (List.map ${decoder(f.getType)} o.${f.getName})")
      case f if f.isOptional => ElmField(f.getName, "Maybe " + asElmType(f.getType), "(maybe " + decoder(f.getType) + ")", "(maybe " + decoder(f.getType) + s") o.${f.getName}")
      case f => ElmField(f.getName, asElmType(f.getType), decoder(f.getType), s"${decoder(f.getType)} o.${f.getName}")
    }
  }

  def asElmType(t: Type): String = {
    t match {
      case StringType => "String"
      case LongType => "Int"
      case IntegerType => "Int"
      case BooleanType => "Bool"
      case LocalDateTimeType => "Date.Date"
      case LocalTimeType => "Time.Time"
      case t: Type => t.getName
    }
  }

  def decoder(t: Type): String = {
    t match {
      case StringType => "string"
      case LongType => "int"
      case IntegerType => "int"
      case BooleanType => "bool"
      case LocalDateTimeType => "date"
      case LocalTimeType => "time"
      case t: Type => StringUtils.downcaseFirst(t.getName)
    }
  }

  case class ElmField(name: String, elmType: String, decoder: String, encoder: String)

}

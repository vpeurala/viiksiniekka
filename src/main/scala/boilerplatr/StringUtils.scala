package boilerplatr

object StringUtils {
  def upcaseFirst(s: String): String = {
    s.head.toUpper + s.tail
  }

  def downcaseFirst(s: String): String = {
    s.head.toLower + s.tail
  }

  def indent(n: Int)(s: String): String = {
    s.lines.map { l => if (l.trim.isEmpty) "" else " " * n + l }.mkString("\n")
  }

  def ordinaryTextToCamelCase(s: String): String = {
    downcaseFirst(s.foldLeft(("", false)) {
      case ((agg, _), c) if !c.isLetterOrDigit => (agg, true)
      case ((agg, true), c) => (agg + c.toUpper, false)
      case ((agg, false), c) => (agg + c, false)
    }._1)
  }

  def ordinaryTextToConstantCase(s: String): String = {
    s.replace(' ', '_').toUpperCase
  }

  def camelCaseToConstantCase(s: String): String = {
    val s1 = s.map(c => if (c.isUpper) {
      s"_${c}"
    } else {
      c.toString
    }).mkString.toUpperCase
    if (s1.startsWith("_")) {
      s1.tail
    } else {
      s1
    }
  }

  def camelCaseToSnakeCase(s: String) = {
    s.foldLeft("") {
      case (agg, c) if c.isUpper && agg != "" => s"${agg}_${c.toLower}"
      case (agg, c) => agg + c.toLower
    }
  }
}

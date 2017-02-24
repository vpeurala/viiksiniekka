package boilerplatr

trait JavaGenerator extends Generator {
  def packageDeclaration(p: Package): String = {
    p match {
      case OrdinaryPackage(packageName: String) => "package " + packageName + ";\n"
      case PredefPackage => ""
    }
  }

  def renderImports(importedClasses: Seq[String]): String = {
    val importsByTld: Map[String, Seq[String]] = importedClasses.
      sorted.
      distinct.
      groupBy(s => s.split('.').head)

    importsByTld.toSeq.
      sortBy { case (tld: String, _) => tld }.
      map { case (_, imports: List[String]) => imports.sortBy { s: String => s }.map(i => s"import ${i};").mkString("\n") }.
      mkString("\n\n")
  }
}

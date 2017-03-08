package viiksiniekka

class JavaRepositoryInterfaceGenerator extends JavaGenerator {
  override def generate(domain: Domain): Map[String, String] = {
    val elems: Seq[(String, String)] =
      domain.getRepositories.map { r =>
        val sourceFilePath = ProjectStructure.SrcMainJava + "/" + domain.getRootPackage.getRepositoryPackage.name.replace('.', '/') + '/' + r.name + ".java"
        val sourceFileContent = generateSingle(domain)(r)
        (sourceFilePath, sourceFileContent)
      }
    Map(elems: _*)
  }

  def generateSingle(domain: Domain)(repository: Repository): String = {
    s"""
       |
     """.stripMargin
  }
}

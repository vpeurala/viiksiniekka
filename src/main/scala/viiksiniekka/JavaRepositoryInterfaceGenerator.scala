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
    s"""${packageDeclaration(domain.getRootPackage.getRepositoryPackage)}
       |${imports(domain)(repository)}
       |
       |public interface ${repository.name} {
       |${repository.operations.map(renderOperation).mkString("\n")}
       |}
       |""".stripMargin
  }

  def imports(domain: Domain)(repository: Repository): String = {
    val importedClasses: Seq[String] = repository.operations.flatMap {
      case ReadRepositoryOperation(_, _, _, outputType) => {
        outputType match {
          case ListType(containedType) => Seq("java.util.List", importForSimpleType(containedType))
          case t: Type => Seq(importForSimpleType(t))
        }
      }
    }
    renderImports(importedClasses)
  }

  // TODO vpeurala 8.3.2017: This does not work correctly yet, only just enough to pass the tests
  def importForSimpleType(t: Type): String = {
    t.getPackage.getDataPackage.name + "." + t.getName
  }

  def renderOperation(operation: RepositoryOperation): String = {
    operation match {
      case ReadRepositoryOperation(name, _, _, output) => s"    ${renderOutputType(output)} ${name}();"
    }
  }

  def renderOutputType(outputType: Type): String = {
    outputType match {
      case ListType(containedType) => s"List<${containedType.getName}>"
    }
  }
}

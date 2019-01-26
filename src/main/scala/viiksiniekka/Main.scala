package viiksiniekka

import java.io.{File, FileInputStream, PrintWriter}

import org.rogach.scallop._

import scala.xml.{Elem, XML}

class ScallopConfiguration(arguments: Seq[String]) extends ScallopConf(arguments) {
  version("viiksiniekka 0.2.0 (c) 2019 Ville Peurala")
  val kotlin = opt[Boolean](required = false)
  val java = opt[Boolean](required = false)
  val elm = opt[Boolean](required = false)
  val domainFileName = trailArg[File]()
  validateFileExists(domainFileName)
  validateFileIsFile(domainFileName)
  verify()
}

object Main {
  def main(args: Array[String]): Unit = {
    type Filename = String
    type SourceCode = String
    type GeneratedFiles = Map[Filename, SourceCode]

    val conf = new ScallopConfiguration(args)

    val domain: Domain = fileToDomain(conf.domainFileName())

    val javaObjects: GeneratedFiles = new JavaDataGenerator().generate(domain)
    val javaBuilders: GeneratedFiles = new JavaBuilderGenerator().generate(domain)
    val javaExamples: GeneratedFiles = new JavaExamplesGenerator().generate(domain)
    val sqlTableCreationScript: GeneratedFiles = new CreateDatabaseSqlGenerator().generate(domain)
    val elmObjects: GeneratedFiles = new ElmDataGenerator().generate(domain) ++
      new ElmDecoderGenerator().generate(domain) ++
      new ElmEncoderGenerator().generate(domain)

    val generatedFiles: GeneratedFiles = javaObjects ++
      javaBuilders ++
      javaExamples ++
      elmObjects

    val mkdir = new CachedMkdir

    generatedFiles.foreach { case (fileName: String, content: String) =>
      mkdir.mkdirp(fileName)
      val generatedFile = new File(fileName)
      generatedFile.createNewFile()
      val pw = new PrintWriter(generatedFile)
      pw.write(content)
      pw.close()
      println(s"Generated file ${generatedFile}")
    }

    println(DomainPrettyPrinter.pp(domain))
  }

  def printUsage(): Unit = println(
    s"""Usage: viiksiniekka FILE
       |
     """.stripMargin)

  /**
    * Based on https://gist.github.com/capotej/1975463.
    */
  class CachedMkdir {
    var cache = Set[String]()

    def mkdirp(path: String) {
      var prepath = ""

      for (dir <- path.split("/").init) {
        prepath += (dir + "/")
        if (!cache.contains(prepath)) {
          new java.io.File(prepath).mkdir()
          cache += prepath
        }
      }
    }
  }

  def fileToDomain(f: File) = {
    val fis = new FileInputStream(f)
    val xml: Elem = XML.load(fis)
    val domainEl: DomainEl = DomainXmlParser.fromXml(xml)
    val domain: Domain = DomainXmlTransformer.toDomain(domainEl)
    domain
  }
}

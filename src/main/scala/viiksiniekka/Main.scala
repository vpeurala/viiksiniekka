package viiksiniekka

import java.io.{File, FileInputStream, PrintWriter}
import org.rogach.scallop._
import scala.xml.{Elem, XML}

class Configuration(arguments: Seq[String]) extends ScallopConf(arguments) {
  val apples = opt[Int](required = true)
  val bananas = opt[Int]()
  val name = trailArg[String]()
  verify()
}

object Main {
  def main(args: Array[String]): Unit = {
    val conf = new Configuration(args)
    println("Apples are: " + conf.apples())

    if (args.length != 1) {
      println("Missing argument FILE.")
      printUsage()
      System.exit(1)
    }
    val arg: String = args(0)
    val file = new File(arg)
    if (!file.exists) {
      println(s"""File does not exist: ${file.getAbsolutePath}""")
      printUsage()
      System.exit(2)
    }

    if (!file.canRead) {
      println(s"""File exists but cannot be read: ${file.getAbsolutePath}""")
      printUsage()
      System.exit(3)
    }

    val fis = new FileInputStream(file)
    val xml: Elem = XML.load(fis)
    val domainEl: DomainEl = DomainXmlParser.fromXml(xml)
    val domain: Domain = DomainXmlTransformer.toDomain(domainEl)

    val javaObjects: Map[String, String] = new JavaDataGenerator().generate(domain)
    val javaBuilders: Map[String, String] = new JavaBuilderGenerator().generate(domain)
    val javaExamples: Map[String, String] = new JavaExamplesGenerator().generate(domain)
    val sqlTableCreationScript: String = new CreateDatabaseSqlGenerator().generateSum(domain)
    val elmObjects: Map[String, String] = new ElmDataGenerator().generate(domain) ++
      new ElmDecoderGenerator().generate(domain) ++
      new ElmEncoderGenerator().generate(domain)

    val generatedFiles: Map[String, String] = javaObjects ++
      javaBuilders ++
      javaExamples ++
      Map("src/main/resources/db/migration/V1__Initial_structure.sql" -> sqlTableCreationScript) ++
      elmObjects

    val mkdir = new CachedMkdir

    generatedFiles.foreach { case (fileName: String, content: String) =>
      mkdir.mkdirp(fileName)
      val generatedFile = new File(fileName)
      generatedFile.createNewFile()
      val pw = new PrintWriter(generatedFile)
      pw.write(content)
      pw.close()
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
}

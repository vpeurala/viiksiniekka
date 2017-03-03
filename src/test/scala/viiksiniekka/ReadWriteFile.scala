package viiksiniekka

import java.io.{File, FileWriter}

import scala.io.Source

trait ReadWriteFile {
  def readFile(name: String): String = {
    Source.fromFile(new File(name), "UTF-8").mkString
  }

  def writeFile(name: String, content: String): Unit = {
    val f = new File(name)
    val fw = new FileWriter(f)
    fw.write(content)
    fw.flush()
    fw.close()
  }
}

package boilerplatr

trait Generator {
  def generate(domain: Domain): Map[String, String]
}

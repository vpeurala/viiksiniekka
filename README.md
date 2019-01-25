# A code generator to end all code generators.

# Local environment setup
  * Install sdkman [https://sdkman.io/install]
  * git clone git@github.com:vpeurala/viiksiniekka.git
  * cd viiksiniekka
  * sbt install java 8.0.201-oracle
  * sbt install scala 2.12.8
  * sdk install sbt 1.2.8
  * sbt clean test

# Measuring test coverage
  * sbt clear coverage test coverageReport

# Publishing & using
  * sbt assembly
  * do things with the assembled uberjar


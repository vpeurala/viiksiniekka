# A code generator to end all code generators.

# Local environment setup
  * Install sdkman [https://sdkman.io/install]
  * git clone git@github.com:vpeurala/viiksiniekka.git
  * cd viiksiniekka
  * sdk install java 8.0.201-oracle
  * sdk install scala 2.12.8
  * sdk install sbt 1.2.8
  * sbt clean test

# Measuring test coverage
  * sbt clean coverage test coverageReport

# Publishing & using
  * sbt assembly
  * do things with the assembled uberjar


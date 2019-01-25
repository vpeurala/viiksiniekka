ThisBuild / scalaVersion := "2.12.8"

ThisBuild / sbtVersion := "1.2.8"

ThisBuild / libraryDependencies += "com.h2database" % "h2" % "1.4.193"
ThisBuild / libraryDependencies += "commons-io" % "commons-io" % "2.5"
ThisBuild / libraryDependencies += "junit" % "junit" % "4.12"
ThisBuild / libraryDependencies += "org.jooq" % "jooq" % "3.9.1"
ThisBuild / libraryDependencies += "org.parboiled" %% "parboiled" % "2.1.3"
ThisBuild / libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.1.1"
ThisBuild / libraryDependencies += "org.rogach" %% "scallop" % "3.1.5"
ThisBuild / libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
ThisBuild / libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test

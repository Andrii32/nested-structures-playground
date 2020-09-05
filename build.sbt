import Dependencies._

ThisBuild / scalaVersion     := "2.13.2"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"
ThisBuild / scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-language:_",
  "-unchecked",
  // "-Wunused:_",
  "-Xfatal-warnings",
  "-Ymacro-annotations"
)

lazy val root = (project in file("."))
  .settings(
    name := "nested-transformations-playground",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += fs2Core,
    libraryDependencies += shapeless,
    libraryDependencies += simulacrum
  )


// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
// https://mvnrepository.com/artifact/org.scalamacros/paradise


import sbt._

object Dependencies {
  lazy val scalaTest =          "org.scalatest" %% "scalatest" % "3.1.1"
  lazy val fs2Core =            "co.fs2" %% "fs2-core" % "2.4.2"
  lazy val shapeless =          "com.chuusai" %% "shapeless" % "2.3.3"
  lazy val simulacrum =         "org.typelevel" %% "simulacrum" % "1.0.0"
}
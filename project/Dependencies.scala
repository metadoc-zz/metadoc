import sbt._

object Dependencies {
  val metaVersion = "0.1.0-SNAPSHOT"

  def reflect(sv: String) = "org.scala-lang" % "scala-reflect" % sv
  def compiler(sv: String) = "org.scala-lang" % "scala-compiler" % sv
  lazy val scalatest = "org.scalatest" %% "scalatest" % "2.1.3" % "test"
  lazy val scalacheck = "org.scalacheck" %% "scalacheck" % "1.11.3" % "test"

  lazy val scalameta = "org.scalameta" % "scalameta" % metaVersion cross CrossVersion.binary
  lazy val scalahost = "org.scalameta" % "scalahost" % metaVersion cross CrossVersion.full
}
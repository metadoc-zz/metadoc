import sbt._

object Dependencies {
  val metaVersion = "0.1.0-SNAPSHOT"
  def compiler(sv: String) = "org.scala-lang" % "scala-compiler" % sv % "optional"
  lazy val scalameta = "org.scalameta" % "scalameta" % metaVersion % "optional" cross CrossVersion.binary
  lazy val scalahost = "org.scalameta" % "scalahost" % metaVersion % "optional" cross CrossVersion.full
}
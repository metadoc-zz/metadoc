import sbt._
import sbt.Keys._
import bintray.Keys._

lazy val commonSettings = Seq(
  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    "-language:existentials",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-language:experimental.macros",
    "-unchecked",
    "-Xfatal-warnings",
    "-Xlint",
    "-Yno-adapted-args",
    "-Ywarn-dead-code",
    "-Ywarn-numeric-widen",
    "-Ywarn-value-discard",
    "-Xfuture"
  )
)

lazy val buildSettings = Seq(
  version := "0.1.0",
  organization := "com.scalakata.metadoc",
  homepage := Some(url("http://scalakata.com")),
  licenses := Seq("MIT" -> url("http://www.opensource.org/licenses/mit-license.html")),
  crossVersion := CrossVersion.full,
  scalaVersion := "2.11.6",
  crossScalaVersions := Seq("2.11.6")
)

lazy val bintrayMaven = Seq(
  repository in bintray := "maven",
  bintrayOrganization in bintray := None
)

lazy val bintrayPlugin = Seq(
    repository in bintray := "sbt-plugins",
    bintrayOrganization in bintray := None
)

val metaVersion = "0.1.0-SNAPSHOT"
def compiler(sv: String) = "org.scala-lang" % "scala-compiler" % sv % "optional"
lazy val scalameta = "org.scalameta" % "scalameta" % metaVersion % "optional" cross CrossVersion.binary
lazy val scalahost = "org.scalameta" % "scalahost" % metaVersion % "optional" cross CrossVersion.full
lazy val scalaz = "org.scalaz" %% "scalaz-core" % "7.1.1"
lazy val specs = "org.specs2" %% "specs2-core" % "3.1" % "test"

lazy val metadocSettings = buildSettings ++ commonSettings// ++ bintraySettings

lazy val model = project
  .settings(metadocSettings: _*)
  .settings(bintrayMaven: _*)
  .settings(
    name := "metadoc-model",
    libraryDependencies ++= Seq(scalaz, specs)
  )

lazy val compilerPlugin = project
  .settings(metadocSettings: _*)
  .settings(bintrayMaven: _*)
  .settings(Merge.settings: _*)
  .settings(seq(
    name := "metadoc-compiler-plugin",
    parallelExecution in Test := false, // hello, reflection sync!!
    libraryDependencies ++= Seq(
      compiler(scalaVersion.value),
      scalameta,
      scalahost
    )
  ): _*)
  .dependsOn(model)

lazy val metaSbtPlugin = project
  .settings(metadocSettings: _*)
  .settings(buildInfoSettings: _*)
  .settings(bintrayPlugin: _*)
  .settings(Seq(
    name := "metadoc-sbt-plugin",
    sbtPlugin := true,
    sourceGenerators in Compile <+= buildInfo,
    buildInfoKeys := Seq[BuildInfoKey](version),
    buildInfoPackage := organization.value + ".build",
    scalaVersion := "2.10.4"
  ): _*)

lazy val bintrayScape = project
  .settings(metadocSettings: _*)
  .settings(Seq(
    name := "bintray-scraper",
    resolvers ++=  Seq(
      "Sonatype" at "https://oss.sonatype.org/content/repositories/releases",
      "spray repo" at "http://repo.spray.io"
    )

    libraryDependencies ++= Seq(
      "io.spray" %% "spray-client" % "1.3.2-20140909",
      "com.typesafe.akka" %% "akka-slf4j" % "2.3.6",
      "ch.qos.logback" % "logback-classic" % "1.0.0" % "runtime",
      "com.typesafe.akka" %% "akka-actor" % "2.3.6",
      "com.typesafe.play" %% "play-json" % "2.4-2014-06-14-ea7daf3"
    )

  ): _*)
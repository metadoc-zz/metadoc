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
  scalaVersion := "2.11.5",
  crossScalaVersions := Seq("2.11.5")
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

lazy val metadocSettings = buildSettings ++ commonSettings ++ bintraySettings

lazy val model = project
  .settings(metadocSettings: _*)
  .settings(bintrayMaven: _*)
  .settings(moduleName := "metadoc-model")

lazy val compilerPlugin = project
  .settings(metadocSettings: _*)
  .settings(moduleName := "metadoc-compiler-plugin")
  .settings(bintrayMaven: _*)
  .settings(Merge.settings: _*)
  .settings(seq(
    parallelExecution in Test := false, // hello, reflection sync!!
    libraryDependencies ++= Seq(
      compiler(scalaVersion.value),
      scalameta,
      scalahost
    )
  ): _*)
  .dependsOn(model)

lazy val metaSbtPlugin = project
  .settings(buildInfoSettings: _*)
  .settings(bintrayPlugin: _*)
  .settings(Seq(
    moduleName := "metadoc-sbt-plugin",
    sbtPlugin := true,
    sourceGenerators in Compile <+= buildInfo,
    buildInfoKeys := Seq[BuildInfoKey](version),
    buildInfoPackage := "com.scalakata.metadoc.build",
    scalaVersion := "2.10.4"
  ): _*)
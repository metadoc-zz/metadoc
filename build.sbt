import sbt._
import sbt.Keys._
import bintray.Keys._
import scala.concurrent.duration._

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
    // "-Xfatal-warnings",
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
  scalaVersion := "2.11.7",
  resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
  scalacOptions in Test ++= Seq("-Yrangepos")
)

lazy val bintrayMaven = Seq(
  repository in bintray := "maven",
  bintrayOrganization in bintray := None
)

lazy val bintrayPlugin = Seq(
    repository in bintray := "sbt-plugins",
    bintrayOrganization in bintray := None
)

lazy val loadSubmodules =
  initialize := {
    val run = initialize.value
    "git submodule init" ! sLog.value
    "git submodule update" ! sLog.value
  }


val metaVersion = "0.1.0-SNAPSHOT"
def compiler(sv: String) = "org.scala-lang" % "scala-compiler" % sv % "optional"
val scalameta = "org.scalameta" % "scalameta" % metaVersion % "optional" cross CrossVersion.binary
val scalahost = "org.scalameta" % "scalahost" % metaVersion % "optional" cross CrossVersion.full
val scalaz = "org.scalaz" %% "scalaz-core" % "7.1.1"
val specs = "org.specs2" %% "specs2-core" % "3.1" % "test"

val pickle = "org.scala-lang.modules" %% "scala-pickling" % "0.10.0"

lazy val metadocSettings = buildSettings ++ commonSettings ++ bintraySettings

lazy val model = project
  .settings(metadocSettings: _*)
  .settings(bintrayMaven: _*)
  .settings(
    name := "metadoc-model",
    libraryDependencies ++= Seq(
      scalaz, 
      specs,
      pickle,
      "me.lessis" %% "semverfi" % "0.1.5-SNAPSHOT",
      "com.github.nscala-time" %% "nscala-time" % "2.0.0"
    ),
    timeout in Backend := 240.seconds
  ).enablePlugins(ScalaKataPlugin)

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
      scalahost,
      specs
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
    scalaVersion := "2.10.5"
  ): _*)

lazy val bintrayScape = project
  .settings(loadSubmodules)
  .settings(metadocSettings: _*)
  .settings(Seq(
    name := "bintray-scraper",
    resolvers ++=  Seq(
      "Sonatype" at "https://oss.sonatype.org/content/repositories/releases",
      "spray repo" at "http://repo.spray.io"
    ),
    libraryDependencies ++= Seq(
      "io.spray" %% "spray-client" % "1.3.2-20140909",
      "com.typesafe.akka" %% "akka-slf4j" % "2.3.6",
      "ch.qos.logback" % "logback-classic" % "1.0.0" % "runtime",
      "com.typesafe.akka" %% "akka-actor" % "2.3.6",
      "com.typesafe.play" %% "play-json" % "2.4.0-M3"
    )
  ): _*) dependsOn(model)

def usePlugin(plugin: ProjectReference) = {
  scalacOptions ++= {
    val jar = (Keys.`package` in (plugin, Compile)).value
    System.setProperty("sbt.paths.plugin.jar", jar.getAbsolutePath)
    Seq("-Xplugin:" + jar.getAbsolutePath, "-Jdummy=" + jar.lastModified)
  }
}

lazy val testBench = project
  .settings(usePlugin(compilerPlugin))
  .settings(
    scalaVersion := "2.11.7"
  )
import sbt._
import Keys._
import sbtassembly.Plugin._
import AssemblyKeys._
import bintray.Plugin._

object Settings {
  lazy val languageVersion = "2.11.5"

  lazy val sharedSettings: Seq[sbt.Def.Setting[_]] = Defaults.defaultSettings ++ Seq(
    version := "0.1.0",
    organization := "com.scalakata.metadoc",
    description := "scaladoc reloaded",
    scalaVersion := languageVersion,
    homepage := Some(url("http://scalakata.com")),
    licenses := Seq("MIT" -> url("http://www.opensource.org/licenses/mit-license.html")),
    crossVersion := CrossVersion.full,
    resolvers ++=  Seq(
      Resolver.sonatypeRepo("snapshots"),
      Resolver.sonatypeRepo("releases")
    ),
    scalacOptions ++= Seq("-feature", "-deprecation", "-unchecked"),
    parallelExecution in Test := false, // hello, reflection sync!!
    logBuffered := false,
    scalaHome := {
      val scalaHome = System.getProperty("example.scala.home")
      if (scalaHome != null) {
        println(s"Going for custom scala home at $scalaHome")
        Some(file(scalaHome))
      } else None
    }
  )

  lazy val mergeDependencies: Seq[sbt.Def.Setting[_]] = assemblySettings ++ Seq(
    test in assembly := {},
    logLevel in assembly := Level.Error,
    jarName in assembly := name.value + "_" + scalaVersion.value + "-" + version.value + "-assembly.jar",
    assemblyOption in assembly ~= { _.copy(includeScala = false) },
    mergeStrategy in assembly := {
      case "scalac-plugin.xml" => MergeStrategy.first
      case x => (mergeStrategy in assembly).value(x)
    },
    Keys.`package` in Compile := {
      val slimJar = (Keys.`package` in Compile).value
      val fatJar = new File(crossTarget.value + "/" + (jarName in assembly).value)
      val _ = assembly.value
      IO.copy(List(fatJar -> slimJar), overwrite = true)
      slimJar
    },
    packagedArtifact in Compile in packageBin := {
      val temp = (packagedArtifact in Compile in packageBin).value
      val (art, slimJar) = temp
      val fatJar = new File(crossTarget.value + "/" + (jarName in assembly).value)
      val _ = assembly.value
      IO.copy(List(fatJar -> slimJar), overwrite = true)
      (art, slimJar)
    }
  )
}

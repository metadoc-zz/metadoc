import sbt._
import Keys._

object ExampleBuild extends Build {
  import Dependencies._
  import Settings._

  lazy val metadoc = Project(
    id   = "metadoc",
    base = file("compilerPlugin"),
    settings = publishableSettings ++ mergeDependencies ++ Seq(
      organization := "com.scalakata.metadoc",
      name := "metadoc-compiler-plugin",
      description := "scaladoc reloaded",
      libraryDependencies ++= Seq(
        compiler(languageVersion),
        scalameta,
        scalahost
      )
    )
  )

  lazy val sbtPlugin = Project(
    id = "sbtPlugin",
    base = file("sbtPlugin"),
    settings = sharedSettings ++ Seq(
      organization := "com.scalakata.metadoc",
      name := "metadoc-sbt-plugin",
      description := "scaladoc reloaded"
    )
}
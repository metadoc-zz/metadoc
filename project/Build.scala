import sbt._
import Keys._
import _root_.bintray.Plugin._
import _root_.bintray.Keys._

import sbtbuildinfo.Plugin._

object ExampleBuild extends Build {
  import Dependencies._
  import Settings._

  lazy val metadocCompilerPlugin = Project(
    id   = "metadoc-compiler-plugin",
    base = file("metadoc-compiler-plugin"),
    settings = 
      sharedSettings ++ 
      mergeDependencies ++ 
      bintraySettings ++ 
      Seq(
        scalaVersion := "2.11.5",
        name := "metadoc-compiler-plugin",
        repository in bintray := "maven",
        bintrayOrganization in bintray := None,
        libraryDependencies ++= Seq(
          compiler(languageVersion),
          scalameta,
          scalahost
      )
    )
  )

  lazy val metadocSbtPlugin = Project(
    id = "metadoc-sbt-plugin",
    base = file("metadoc-sbt-plugin"),
    settings = sharedSettings ++ Seq(
      sbtPlugin := true,
      name := "metadoc-sbt-plugin",
      repository in bintray := "sbt-plugins",
      bintrayOrganization in bintray := None,
      scalaVersion := "2.10.4",
      scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")
    ) ++ buildInfoSettings
      ++ Seq(
          sourceGenerators in Compile <+= buildInfo,
          buildInfoKeys := Seq[BuildInfoKey](version),
          buildInfoPackage := "com.scalakata.metadoc.build"
      ) ++ bintraySettings
  )
}
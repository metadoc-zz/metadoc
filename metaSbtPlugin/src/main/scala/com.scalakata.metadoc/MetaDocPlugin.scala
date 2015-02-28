package com.scalakata.metadoc

import sbt._
import Keys._

import com.scalakata.metadoc.build.BuildInfo.version

object MetaDocPlugin extends Plugin {

  // TODO: dont monopolyze compile
  // metadoc dependsOn command
  // libraryDependencies in metadoc +=
  //  compilerPlugin("com.scalakata.metadoc" %% "metadoc-compiler-plugin" % version % "metadoc")
  // scalacOptions in metadoc += "-Ystop-after:metadoc"

  lazy val metadocSettings = Seq(
    libraryDependencies in ThisBuild += 
      compilerPlugin("com.scalakata.metadoc" % "metadoc-compiler-plugin" % version cross CrossVersion.full),

    resolvers in ThisBuild += 
      "bintray-masseguillaume-maven" at "http://dl.bintray.com/content/masseguillaume/maven/"
  )
}
package com.scalakata.metadoc

object MetaDocPlugin extends Plugin {
  lazy val settings = Seq(
    libraryDependencies in ThisBuild += 
      compilerPlugin("com.scalakata.metadoc" % "metadoc-compiler-plugin")
  )
}
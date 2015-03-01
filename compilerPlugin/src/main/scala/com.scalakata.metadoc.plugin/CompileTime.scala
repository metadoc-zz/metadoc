package com.scalakata.metadoc
package plugin

import scala.meta.internal.ast._
import scala.meta.internal.hosts.scalac._

import scala.meta.ui._

trait CompileTime {
  val global: scala.tools.nsc.Global
  implicit val c = Scalahost.mkSemanticContext(global)

  def example(sources: List[Source]): Unit = {
    val packages = for {
      Source(cs) <- sources
      Pkg(name, cs2) <- cs
    } yield(model.Pkg(name.show[Code], Nil))

    println(packages)
    // println(sources.map( _.show[Raw]).mkString(System.lineSeparator))
    // ()
  }
}

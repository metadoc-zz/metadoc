package com.scalakata.metadoc
package plugin

import scala.meta.internal.ast._
import scala.meta.internal.hosts.scalac._

import scala.meta.ui._

trait CompileTime {
  val global: scala.tools.nsc.Global
  implicit val c = Scalahost.mkSemanticContext(global)

  def loop(ss: Seq[Stat]): Seq[model.Tree] = ss.flatMap{
    case Pkg(ref, stats) => Seq(model.Pkg(ref.show[Raw], loop(stats.toSeq)))
    case Defn.Object(mods, name, ctor, templ) => Seq(model.Object(name.show[Raw]))
    case Defn.Trait(mods, name, tparams, ctor, templ) => Seq(model.Trait(name.show[Raw]))
    case Defn.Class(mods, name, tparams, ctor, templ) => Seq(model.Class(name.show[Raw]))
    case Import(clauses) => Seq()
    case Pkg.Object(mods, name, ctor, templ) => Seq()
    case huh => sys.error(huh.show[Raw])
  }
  

  def example(sources: List[Source]): Unit = {
    println(sources.flatMap{ case Source(stats) => loop(stats.toSeq)})
  }
}

package com.scalakata.metadoc
package plugin

import scala.meta.internal._
import scala.meta.internal.ast._
import scala.meta.internal.hosts.scalac._
import scala.meta.Scalahost
import scala.meta.ui._

import scalaz.syntax.show._
import scala.meta.dialects.Scala211

import scalaz.std.option._
import scalaz.syntax.std.option._
import scalaz.syntax.optional._
import scalaz.syntax.semigroup._

trait Metadoc {
  val global: scala.tools.nsc.Global
  implicit val c = Scalahost.mkGlobalContext(global)

  def example(sources: List[Source]): Unit = {

    def mergePackages(ss: Seq[Stat], acc: Map[Option[Term.Ref], Set[Stat]] = Map(),
       pkg: Option[Term.Ref] = None): Map[Option[Term.Ref], Set[Stat]] = {
      ss.foldLeft(acc){
        case (acc2, ast.Source(ss)) => mergePackages(ss, acc2, pkg)
        case (acc2, Pkg(ref, stats)) => mergePackages(stats, acc2, pkg |+| Some(ref))
        case (acc2, e) => {
          acc2.updated(pkg,
            acc2.get(pkg).getOrElse(Set()) + e
          )
        }
      }
    }

    val out =
      mergePackages(sources).toList
      .map{ case(pkg, stats) => (pkg.map(_.show[Code]) | "", stats)}
      .sortBy{ case(pkg, _) => pkg}
      .map{ case(pkg, stats) =>
        println(stats.map(_.show[Raw]).mkString(System.lineSeparator))
        val ms = stats.toList.flatMap{
          case Defn.Object(mods, Term.Name(name), ctor, templ) => {
            List(model.Object(name))
          }

          case Defn.Trait(mods, Type.Name(name), tparams, ctor, templ) =>
            List(model.Trait(name))
          case c @ Defn.Class(mods, Type.Name(name), tparams, ctor, templ) =>
            List(model.Class(name))
          case Import(clauses) => List()
          case Pkg.Object(mods, Term.Name(name), ctor, templ) => List()
          case e => println(e.show[Raw]); List()
        }

        model.Pkg(pkg, ms.toSet)
      }

    println(out.map(_.shows).mkString(System.lineSeparator))

    ()
  }
}
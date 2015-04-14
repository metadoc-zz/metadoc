package com.scalakata.metadoc
package plugin

import scala.meta.internal._
import ast._
import scala.meta.internal.hosts.scalac._
import scala.meta.Scalahost

import scala.meta.ui._

import scalaz.syntax.show._

trait Metadoc {
  val global: scala.tools.nsc.Global
  implicit val c = Scalahost.mkGlobalContext(global)

  import scalaz._
  import Scalaz._


  implicit object TermRefSemigroup extends Semigroup[Term.Ref] {
    def append(a: Term.Ref, b: => Term.Ref) = {
      (a, b) match {
        case (Term.Select(a, b), Term.Name(c)) => Term.Select(Term.Select(a, b), Term.Name(c))
      }
    }
  }

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

    println(mergePackages(sources))

    ()
  }
}

  // // Seq[model.Tree] = ss.flatMap{
    
  //   // case Pkg(tn @ Term.Name(name), stats) => {
  //   //   println((tn, tn.hashCode))
  //   //   // Seq(model.Pkg(name, loop(stats)))
  //   //   loop(stats)
  //   // }

  //   // case Defn.Object(mods, Term.Name(name), ctor, templ) => Seq(model.Object(name))
  //   // case Defn.Trait(mods, Term.Name(name), tparams, ctor, templ) => 
  //   //  Seq(model.Trait(name))
  //   // case Defn.Class(mods, Term.Name(name), tparams, ctor, templ) => 
  //   //  Seq(model.Class(name))
  //   // case Import(clauses) => Seq()
  //   // case Pkg.Object(mods, Term.Name(name), ctor, templ) => Seq()
  //   // case huh => sys.error(huh.show[Raw])
  //   case Pkg(ts, stats) => {
  //     // println(e.show[Raw])
  //     // println((ts, ts.hashCode))
  //     loop(stats, ts + visited)
  //   }
  //   case e => {
  //     visited
  //   }

  //   // Seq(
  //   //   // model.Class(e.getClass.toString),
  //   //   // model.Class(e.show[Raw])
  //   // )
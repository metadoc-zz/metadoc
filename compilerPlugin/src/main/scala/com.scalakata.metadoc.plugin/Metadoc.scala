package com.scalakata.metadoc
package plugin

import scala.meta.internal._
import ast._
import scala.meta.internal.hosts.scalac._
import scala.meta.Scalahost

import scala.meta.ui._

import scalaz.syntax.show._

import scala.pickling.Defaults._
import scala.pickling.binary._

trait Metadoc {
  val global: scala.tools.nsc.Global
  implicit val c = Scalahost.mkGlobalContext(global)

  import scalaz._
  import Scalaz._


  implicit object TermRefSemigroup extends Semigroup[Term.Ref] {
    def append(a: Term.Ref, b: => Term.Ref) = {
      (a, b) match {
        case (ts @ Term.Select(_, _), tn @ Term.Name(_)) => Term.Select(ts, tn)
        case (tn1 @ Term.Name(_), tn2 @ Term.Name(_)) => Term.Select(tn1, tn2)
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

    val out =
      mergePackages(sources).map{ case(pkg, stats) => 
        
        val ms = stats.flatMap{
          case Defn.Object(mods, Term.Name(name), ctor, templ) =>
            Seq(model.Object(name))
          case Defn.Trait(mods, Type.Name(name), tparams, ctor, templ) =>
            Seq(model.Trait(name))
          case c @ Defn.Class(mods, Type.Name(name), tparams, ctor, templ) =>
            Seq(model.Class(name))
          case Import(clauses) => Seq()
          case Pkg.Object(mods, Term.Name(name), ctor, templ) => Seq()
          case e => println(e.show[Raw]); Seq()
        }

        (pkg.map(_.show[Code]) | "", ms)
      }

      println(out)

    // import java.nio.file.{Path, Paths, Files}

    // val f = new File("metadoc.bin")
    // val fw = new FileWriter(f)
    // fw.write(out.pickle)
    // // f.close()
    // fw.close()

    // Files.write(Paths.get("metadoc.bin"), out.pickle.value)

    // out.pickle.value
    // Map(1 -> 2).
    // List(1).pickle.value

    // println(out)

    ()
  }
}
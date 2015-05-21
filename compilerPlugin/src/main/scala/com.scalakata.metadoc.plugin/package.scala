package com.scalakata.metadoc

import scala.meta.internal._
import scala.meta.internal.ast._
import scala.meta.ui._

import scalaz.{Show, Semigroup}
import scalaz.syntax.show._

package object plugin {

  implicit object TreeShow extends Show[model.Tree] {
    override def show(tree: model.Tree) = tree match {
      case model.Trait(name) => s"Trait $name"
      case model.Object(name) => s"Object $name"
      case model.Class(name) => s"Class $name"
    }
  }

  def print[B: Show](root: String, nodes: Seq[B]) = {
    val r = root
    val ns = nodes.map(_.shows)

    val nl = System.lineSeparator
    val m = "├── "
    val f = "└── "
 
    if(ns.isEmpty) r
    else {
      r +
      ns.init.map(nl + m + _).mkString +
      nl + f + ns.last
    }
  }

  implicit object PkgShow extends Show[model.Pkg] {
    override def shows(a: model.Pkg) = print(a.name.toString, a.childs.toList)
  }

  implicit object TermRefSemigroup extends Semigroup[Term.Ref] {
    private def sequence(a: Term): List[Term.Name] = a match {
      case Term.Select(qual, name) => sequence(qual) ::: List(name)
      case name: Term.Name => List(name)
      case e => sys.error(s"boom ${e.show[Raw]}")
    }

    private def buildUp(acc: List[Term.Name]): Term.Ref = acc match {
      case List(a) => a
      case List(a, b) => Term.Select(a, b)
      case a :: b :: r => r.foldLeft(Term.Select(a, b))(Term.Select(_, _))
    }

    def append(a: Term.Ref, b: => Term.Ref) = {
      buildUp(sequence(a) ::: sequence(b))
    }
  }
}


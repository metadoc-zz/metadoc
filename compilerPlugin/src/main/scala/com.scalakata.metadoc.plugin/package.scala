package com.scalakata.metadoc

import scala.meta.internal._
import ast._

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
    def append(a: Term.Ref, b: => Term.Ref) = {
      (a, b) match {
        case (ts @ Term.Select(_, _), tn @ Term.Name(_)) => Term.Select(ts, tn)
        case (tn1 @ Term.Name(_), tn2 @ Term.Name(_)) => Term.Select(tn1, tn2)
      }
    }
  }
}


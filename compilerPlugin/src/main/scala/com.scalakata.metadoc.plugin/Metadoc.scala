package com.scalakata.metadoc
package plugin

import scala.meta.internal.ast._
import scala.meta.internal.hosts.scalac._
import scala.meta.Scalahost

import scala.meta.ui._

import scalaz.syntax.show._



trait Metadoc {
  val global: scala.tools.nsc.Global
  implicit val c = Scalahost.mkGlobalContext(global)

  type PackageGroup = Map[Pkg, List[Stat]]

  // def loop(ss: Seq[Stat], visited: PackageGroup = Map()): PackageGroup = 
  //   ss.foldLeft(Map.empty[Pkg, List[Stat]){
  //     case (acc, v) => 
  //       v match {
  //         case p @ Pkg(_, stats) => {
  //           visited ++ acc
  //           loop(stats,  + p)
  //         }
  //         case e => visited
  //       }
  //   }
  def example(sources: List[Source]): Unit = {
    // val nl = System.lineSeparator
    // val res = sources.foldLeft(Set.empty[Pkg]){ 
    //   case (acc, Source(stats)) => loop(stats) ++ acc
    // }
    // println(res)
    // println(res.size)
  }
}

// package com.example {
//   package a.b {
//     class A
//     package c {
//       class ABC1
//       class ABC2  
//     }
//     package d {
//       class ABD
//       package e {
//         class ABDE1
//         class ABDE2
//       }  
//     }
//   }
// }


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
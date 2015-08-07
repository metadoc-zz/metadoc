// package com.scalakata.metadoc
// package model

// import scalaz.Show
// import scalaz.syntax.show._

// trait ShowInstance {
//   implicit object ShowTree extends Show[Tree] {
//     val nl = System.lineSeparator
//     val indent = 2
//     override def shows(a: Tree): String = {
//       def loop(level: Int)(c: Tree): String = {
//         val padding = " " * level
//         c match {
//           case Trait(name: String) => s"trait $name"
//           case Object(name: String) => s"object $name"
//           case Class(name: String) => s"class $name"
//           // case Pkg(name: String, childs: Seq[Tree]) => {
//           //   List(
//           //     s"package $name {",
//           //     childs.map(loop(level + indent)).map(padding + _).mkString(nl),
//           //     "}"
//           //   ).map(padding + _).mkString(nl)
//           // }
//         }
//       }
//       loop(0)(a)
//     }
//   }  
// }

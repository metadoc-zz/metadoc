package com.scalakata.metadoc.utils

// import scalaz.Monoid

// case class MultiMap[K, C[_], T](v: Map[K, C[T]])(implicit val mo: Monoid[C]) {
//   def +(vs: C[T]): MultiMap[K, C, T] = {
    
//   }
// }

// object MultiMapMonoid {
//   implicit def MapListMonoid[R, T](implicit val mo: Monoid[T]) extends Monoid[Map[R, T]] {
//     def zero = Map.empty[R, T]
//     def mappend(a: Map[R, T], b: Map[R, T]) = {
//       a.foldLeft(zero){ case (acc, v) =>
        
//       }
//     }
//   }
// }

// object BiMap {
//   def apply[A](vs: (A, A)*) = {
//     def append(a: A, vs: Map[A, Set[A]]) = {
//       vs.get(a) match {
//         case Some(s) => vs.updated(a, s + a)
//         case None => vs + (a -> Set(a))
//       }
//     }
//     vs.foldLeft((Map[A, Set[A]](), Map[A, Set[A]]()){ case ((ab, ba), (a, b)) =>
//       (append(a, ab), append(a, ba))
//     }
//   }
// }

// import scalaz.std.set._
// import scalaz.syntax.monoid._
// import scalaz.syntax.options._

// class BiMap[A](ab: Map[A, Set[A]], ba: Map[A, Set[A]]){
//   def rel(a: A, b: A) = {
//     (ab.get(a) |+| ba.get(a)).map(_.contains(b)) | false
//   }
// }

// implicit class BiTuple[A, B]{
//   def <=>(a: A, b: B) = (a, b)
// }

// object Licenses {
//   private val compatibilities = BiMap(
//     Apache_2_0 <=> BSD4Clauses,

//   )

//   def compatible(a: License, b: License) = {
//     compatibilities(a).contains(b)
//   }
// }
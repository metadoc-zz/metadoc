package com.scalakata.metadoc.utils

import scalaz.Monoid

case class MultiMap[K, C[_], T](v: Map[K, C[T]])(implicit val mo: Monoid[C]) {
  def +(vs: C[T]): MultiMap[K, C, T] = {
    
  }
}

object MultiMapMonoid {
  implicit def MapListMonoid[R, T](implicit val mo: Monoid[T]) extends Monoid[Map[R, T]] {
    def zero = Map.empty[R, T]
    def mappend(a: Map[R, T], b: Map[R, T]) = {
      a.foldLeft(zero){ case (acc, v) =>
        
      }
    }
  }
}
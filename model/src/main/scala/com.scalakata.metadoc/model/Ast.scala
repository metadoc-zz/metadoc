package com.scalakata.metadoc
package model
  

trait Tree
case class Trait(name: String) extends Tree
case class Object(name: String) extends Tree
case class Class(name: String) extends Tree

case class Pkg(val name: String, val childs: Set[Tree])

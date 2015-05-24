package com.scalakata.metadoc
package model

trait Tree
case class Pkg(val name: Name, val childs: Set[Tree])

case class Trait(mods: Set[Mod], name: Name) extends Tree
case class Class(mods: Set[Mod], name: Name) extends Tree
case class Object(mods: Set[Mod], name: Name) extends Tree

case class Name(name: String, hash: Int) {
  override def hashCode: Int = hash
  override def equals(o: Any) = o match {
    case Name(_, oHash) => hash == oHash
    case _ => false
  }
}

trait Mod
case class Annot(body: Term) extends Mod
case class Private(name: Name) extends Mod
case class Protected(name: Name) extends Mod
case object Implicit extends Mod
case object Final extends Mod
case object Sealed extends Mod
case object Override extends Mod
case object Case extends Mod
case object Abstract extends Mod
case object Covariant extends Mod
case object Contravariant extends Mod
case object Lazy extends Mod
case class Ffi(signature: String) // Foreign function interface: eg Lscala/collection/immutable/List extends Mod

case class TypeParam(
  mods: Seq[Mod], 
  name: Name, 
  tparams: Seq[TypeParam], 
  bounds: TypeBounds, 
  viewBounds: Seq[Type], 
  contextBounds: Seq[Type]
)
package com.scalamari.velocypack4s.core.format

import com.scalamari.velocypack4s.core._
import com.scalamari.velocypack4s.core.domain._

import scala.collection.LinearSeq
import scala.reflect.ClassTag

private[core] trait CollectionFormats {

  implicit def mapFormat[V](implicit vf: VPackFormat[V]): VPackFormat[Map[String, V]] = new VPackFormat[Map[String, V]] {
    override def write(obj: Map[String, V]): VPackValue = {
      val items = obj.mapValues(_.toVPack).toVector
      VPackObject(items)
    }

    override def read(value: VPackValue): Map[String, V] = value match {
      case VPackObject(fields) => fields.toMap.mapValues(_.convertTo[V])
      case _                   => serializationError(s"MapFormat: not an object: [$value]")
    }
  }

  implicit def optionFormat[T: VPackFormat]: VPackFormat[Option[T]] = new VPackFormat[Option[T]] {

    override def write(value: Option[T]): VPackValue = value.map(_.toVPack).getOrElse(VPackNull)

    override def read(value: VPackValue): Option[T] = value match {
      case VPackNull => None
      case other     => Some(other.convertTo[T])
    }

  }

  implicit def arrayFormat[T: VPackFormat: ClassTag]: VPackFormat[Array[T]] = new VPackFormat[Array[T]] {
    override def read(value: VPackValue): Array[T] = value match {
      case VPackArray(elems) => Array(elems.map(_.convertTo[T]): _*)
      case _                 => deserializationError(s"arrayFormat: Expected an array but got: $value")
    }

    override def write(value: Array[T]): VPackValue = VPackArray(value.map(_.toVPack).toVector)
  }

  implicit def listFormat[A: VPackFormat]: VPackFormat[List[A]] = viaVector[List[A], A](v => List(v: _*))

  implicit def seqFormat[A: VPackFormat]: VPackFormat[Seq[A]] = viaVector[Seq[A], A](v => Seq(v: _*))

  implicit def indexedSeqFormat[A: VPackFormat]: VPackFormat[IndexedSeq[A]] = viaVector[IndexedSeq[A], A](v => IndexedSeq(v: _*))

  implicit def linearSeqFormat[A: VPackFormat]: VPackFormat[LinearSeq[A]] = viaVector[LinearSeq[A], A](v => LinearSeq(v: _*))

  implicit def iterableFormat[A: VPackFormat]: VPackFormat[Iterable[A]] = viaVector[Iterable[A], A](v => Iterable(v: _*))

  import scala.collection.{immutable => imm}

  implicit def immSetFormat[A: VPackFormat]: VPackFormat[imm.Set[A]] = viaVector[Set[A], A](v => Set(v: _*))

  implicit def immSeqFormat[A: VPackFormat]: VPackFormat[imm.Seq[A]] = viaVector[imm.Seq[A], A](v => imm.Seq(v: _*))

  implicit def immIndexedSeqFormat[A: VPackFormat]: VPackFormat[imm.IndexedSeq[A]] = viaVector[imm.IndexedSeq[A], A](v => imm.IndexedSeq(v: _*))

  implicit def immLinearSeqFormat[A: VPackFormat]: VPackFormat[imm.LinearSeq[A]] = viaVector[imm.LinearSeq[A], A](v => imm.LinearSeq(v: _*))

  implicit def immIterableFormat[A: VPackFormat]: VPackFormat[imm.Iterable[A]] = viaVector[imm.Iterable[A], A](v => imm.Iterable(v: _*))

  import scala.collection.{mutable => mut}

  implicit def mutSetFormat[A: VPackFormat]: VPackFormat[mut.Set[A]] = viaVector[mut.Set[A], A](v => mut.Set(v: _*))

  implicit def mutSeqFormat[A: VPackFormat]: VPackFormat[mut.Seq[A]] = viaVector[mut.Seq[A], A](v => mut.Seq(v: _*))

  implicit def mutIndexedSeqFormat[A: VPackFormat]: VPackFormat[mut.IndexedSeq[A]] = viaVector[mut.IndexedSeq[A], A](v => mut.IndexedSeq(v: _*))

  implicit def mutLinearSeqFormat[A: VPackFormat]: VPackFormat[mut.LinearSeq[A]] = viaVector[mut.LinearSeq[A], A](v => mut.LinearSeq(v: _*))

  implicit def mutIterableFormat[A: VPackFormat]: VPackFormat[mut.Iterable[A]] = viaVector[mut.Iterable[A], A](v => mut.Iterable(v: _*))

  implicit def vectorFormat[A: VPackFormat]: VPackFormat[Vector[A]] = viaVector[Vector[A], A](identity)

  private def viaVector[I <: Iterable[A], A: VPackFormat](f: Vector[A] => I): VPackFormat[I] = new VPackFormat[I] {
    override def write(value: I): VPackValue = VPackArray(value.map(_.toVPack).toVector)

    override def read(value: VPackValue): I = value match {
      case VPackArray(elems) => f(elems.map(_.convertTo[A]))
      case _                 => deserializationError(s"Expected an array but got: $value")
    }
  }

}

private[core] object CollectionFormats extends CollectionFormats

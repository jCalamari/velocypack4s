package com.scalamari.velocypack4s.core.encoder

import com.scalamari.velocypack4s.core._
import com.scalamari.velocypack4s.core.domain.{VPackArray, VPackNull, VPackObject, VPackString}

import scala.collection.LinearSeq

private[core] trait CollectionEncoders {

  implicit def listEncoder[A: VPackEncoder]: VPackEncoder[List[A]] = VPackEncoder.createEncoder(list => VPackArray(list.map(_.toVPack).toVector))

  implicit def arrayEncoder[A: VPackEncoder]: VPackEncoder[Array[A]] = VPackEncoder.createEncoder(array => VPackArray(array.map(_.toVPack).toVector))

  implicit def mapEncoder[K, V](implicit keyEncoder: VPackEncoder[K], valueEncoder: VPackEncoder[V]): VPackEncoder[Map[K, V]] =
    VPackEncoder.createEncoder(items =>
      VPackObject(items.toVector.map {
        case (key, value) =>
          keyEncoder.encode(key) match {
            case VPackString(k) => k -> valueEncoder.encode(value)
            case other          => throw new EncodingException(s"Keys must be formatted as strings: [$other]")
          }
      }))

  implicit def optionEncoder[A: VPackEncoder]: VPackEncoder[Option[A]] = VPackEncoder.createEncoder(opt => opt.map(_.toVPack).getOrElse(VPackNull))

  implicit def seqEncoder[A: VPackEncoder]: VPackEncoder[Seq[A]] = viaVector[Seq[A], A]

  implicit def indexedSeqEncoder[A: VPackEncoder]: VPackEncoder[IndexedSeq[A]] = viaVector[IndexedSeq[A], A]

  implicit def linearSeqEncoder[A: VPackEncoder]: VPackEncoder[LinearSeq[A]] = viaVector[LinearSeq[A], A]

  implicit def iterableEncoder[A: VPackEncoder]: VPackEncoder[Iterable[A]] = viaVector[Iterable[A], A]

  import scala.collection.{immutable => imm}

  implicit def immSetEncoder[A: VPackEncoder]: VPackEncoder[imm.Set[A]] = viaVector[Set[A], A]

  implicit def immSeqEncoder[A: VPackEncoder]: VPackEncoder[imm.Seq[A]] = viaVector[imm.Seq[A], A]

  implicit def immIndexedSeqEncoder[A: VPackEncoder]: VPackEncoder[imm.IndexedSeq[A]] = viaVector[imm.IndexedSeq[A], A]

  implicit def immLinearSeqEncoder[A: VPackEncoder]: VPackEncoder[imm.LinearSeq[A]] = viaVector[imm.LinearSeq[A], A]

  implicit def immIterableEncoder[A: VPackEncoder]: VPackEncoder[imm.Iterable[A]] = viaVector[imm.Iterable[A], A]

  import scala.collection.{mutable => mut}

  implicit def mutSetEncoder[A: VPackEncoder]: VPackEncoder[mut.Set[A]] = viaVector[mut.Set[A], A]

  implicit def mutSeqEncoder[A: VPackEncoder]: VPackEncoder[mut.Seq[A]] = viaVector[mut.Seq[A], A]

  implicit def mutIndexedSeqEncoder[A: VPackEncoder]: VPackEncoder[mut.IndexedSeq[A]] = viaVector[mut.IndexedSeq[A], A]

  implicit def mutLinearSeqEncoder[A: VPackEncoder]: VPackEncoder[mut.LinearSeq[A]] = viaVector[mut.LinearSeq[A], A]

  implicit def mutIterableEncoder[A: VPackEncoder]: VPackEncoder[mut.Iterable[A]] = viaVector[mut.Iterable[A], A]

  private def viaVector[I <: Iterable[A], A: VPackEncoder]: VPackEncoder[I] =
    VPackEncoder.createEncoder(iterable => VPackArray(iterable.map(_.toVPack).toVector))

  implicit def vectorEncoder[A: VPackEncoder]: VPackEncoder[Vector[A]] =
    VPackEncoder.createEncoder(iterable => VPackArray(iterable.map(_.toVPack)))

}

private[core] object CollectionEncoders extends CollectionEncoders

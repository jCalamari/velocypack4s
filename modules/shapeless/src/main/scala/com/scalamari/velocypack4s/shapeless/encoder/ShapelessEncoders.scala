package com.scalamari.velocypack4s.shapeless.encoder

import com.scalamari.velocypack4s.core.domain._
import com.scalamari.velocypack4s.core.encoder._
import shapeless._
import shapeless.labelled.FieldType

private[shapeless] trait ShapelessEncoders {

  implicit val hnilEncoder: VPackObjectEncoder[HNil] = VPackObjectEncoder.createObjectEncoder(_ => VPackObject(Vector.empty))

  implicit def hlistObjectEncoder[K <: Symbol, H, T <: HList](
    implicit
    witness:  Witness.Aux[K],
    hEncoder: Lazy[VPackEncoder[H]],
    tEncoder: VPackObjectEncoder[T]
  ): VPackObjectEncoder[FieldType[K, H] :: T] = {
    val attribute: String = witness.value.name
    VPackObjectEncoder.createObjectEncoder { hlist =>
      val head = hEncoder.value.encode(hlist.head)
      val tail = tEncoder.encode(hlist.tail)
      VPackObject(tail.fields :+ (attribute, head))
    }
  }

  implicit def genericObjectEncoder[A, H <: HList](
    implicit
    generic:  LabelledGeneric.Aux[A, H],
    hEncoder: Lazy[VPackObjectEncoder[H]]
  ): VPackEncoder[A] =
    VPackObjectEncoder.createObjectEncoder { value =>
      hEncoder.value.encode(generic.to(value))
    }

}

private[shapeless] object ShapelessEncoders extends ShapelessEncoders

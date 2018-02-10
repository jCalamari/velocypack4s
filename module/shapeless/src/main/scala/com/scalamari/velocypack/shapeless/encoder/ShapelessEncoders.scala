package com.scalamari.velocypack.shapeless.encoder

import com.scalamari.velocypack.core.domain._
import com.scalamari.velocypack.core.encoder.{VPackEncoder, VPackObjectEncoder}
import shapeless._
import shapeless.labelled.FieldType

private[shapeless] trait ShapelessEncoders {

  implicit val stringEncoder: VPackEncoder[String] = VPackEncoder.createEncoder(str => VPackString(str))
  implicit val doubleEncoder: VPackEncoder[Double] = VPackEncoder.createEncoder(num => VPackNumber(num))
  implicit val intEncoder: VPackEncoder[Int] = VPackEncoder.createEncoder(num => VPackNumber(num))
  implicit val booleanEncoder: VPackEncoder[Boolean] = VPackEncoder.createEncoder(bool => VPackBoolean(bool))

  implicit def listEncoder[A](implicit enc: VPackEncoder[A]): VPackEncoder[List[A]] = VPackEncoder.createEncoder(list => VPackArray(list.map(enc.encode)))

  implicit def optionEncoder[A](implicit enc: VPackEncoder[A]): VPackEncoder[Option[A]] = VPackEncoder.createEncoder(opt => opt.map(enc.encode).getOrElse(VPackNull))

  implicit val hnilEncoder: VPackObjectEncoder[HNil] = VPackObjectEncoder.createObjectEncoder(hnil => VPackObject(Nil))

  implicit def hlistObjectEncoder[K <: Symbol, H, T <: HList](
    implicit
    witness: Witness.Aux[K],
    hEncoder: Lazy[VPackEncoder[H]],
    tEncoder: VPackObjectEncoder[T]
  ): VPackObjectEncoder[FieldType[K, H] :: T] = {
    val fieldName: String = witness.value.name
    VPackObjectEncoder.createObjectEncoder { hlist =>
      val head = hEncoder.value.encode(hlist.head)
      val tail = tEncoder.encode(hlist.tail)
      VPackObject((fieldName, head) :: tail.fields)
    }
  }

  implicit def genericObjectEncoder[A, H <: HList](
    implicit
    generic: LabelledGeneric.Aux[A, H],
    hEncoder: Lazy[VPackObjectEncoder[H]]
  ): VPackEncoder[A] =
    VPackObjectEncoder.createObjectEncoder { value =>
      hEncoder.value.encode(generic.to(value))
    }

}

private[shapeless] object ShapelessEncoders extends ShapelessEncoders

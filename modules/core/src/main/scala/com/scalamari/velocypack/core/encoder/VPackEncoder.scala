package com.scalamari.velocypack.core.encoder

import com.scalamari.velocypack.core.domain.VPackValue

private[velocypack] trait VPackEncoder[A] extends Encoder[A, VPackValue] {

  def encode(value: A): VPackValue

}

private[velocypack] object VPackEncoder {

  def apply[A](implicit enc: VPackEncoder[A]): VPackEncoder[A] = enc

  def createEncoder[A](func: A => VPackValue): VPackEncoder[A] = new VPackEncoder[A] {
    def encode(value: A): VPackValue = func(value)
  }

}

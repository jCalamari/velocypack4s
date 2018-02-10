package com.scalamari.velocypack.core.encoder

import com.scalamari.velocypack.core.domain.VPackObject

private[velocypack] trait VPackObjectEncoder[A] extends VPackEncoder[A] {

  def encode(value: A): VPackObject

}

private[velocypack] object VPackObjectEncoder {

  def createObjectEncoder[A](fn: A => VPackObject): VPackObjectEncoder[A] = new VPackObjectEncoder[A] {
    def encode(value: A): VPackObject = fn(value)
  }

}

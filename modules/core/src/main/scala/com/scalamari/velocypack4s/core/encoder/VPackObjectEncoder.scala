package com.scalamari.velocypack4s.core.encoder

import com.scalamari.velocypack4s.core.domain._

private[velocypack4s] trait VPackObjectEncoder[A] extends VPackEncoder[A] {

  def encode(value: A): VPackObject

}

private[velocypack4s] object VPackObjectEncoder {

  def createObjectEncoder[A](fn: A => VPackObject): VPackObjectEncoder[A] = new VPackObjectEncoder[A] {
    def encode(value: A): VPackObject = fn(value)
  }

}

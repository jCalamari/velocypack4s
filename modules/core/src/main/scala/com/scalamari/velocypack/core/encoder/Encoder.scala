package com.scalamari.velocypack.core.encoder

trait Encoder[A, B] {

  def encode(value: A): B

}

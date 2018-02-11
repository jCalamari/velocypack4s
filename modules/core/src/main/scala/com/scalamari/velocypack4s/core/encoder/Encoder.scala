package com.scalamari.velocypack4s.core.encoder

trait Encoder[A, B] {

  def encode(value: A): B

}

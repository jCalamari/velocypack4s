package com.scalamari.velocypack.core.decoder

trait Decoder[A, B] {

  def decode(value: B): A

}

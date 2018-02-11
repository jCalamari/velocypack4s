package com.scalamari.velocypack4s.core.decoder

trait Decoder[A, B] {

  def decode(value: B): A

}

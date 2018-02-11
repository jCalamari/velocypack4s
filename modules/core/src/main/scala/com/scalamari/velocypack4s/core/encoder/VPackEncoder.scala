package com.scalamari.velocypack4s.core.encoder

import com.scalamari.velocypack4s.core.domain._

private[velocypack4s] trait VPackEncoder[A] extends Encoder[A, VPackValue] {

  def encode(value: A): VPackValue

}

private[velocypack4s] object VPackEncoder {

  def apply[A](implicit enc: VPackEncoder[A]): VPackEncoder[A] = enc

  def createEncoder[A](func: A => VPackValue): VPackEncoder[A] = new VPackEncoder[A] {
    def encode(value: A): VPackValue = func(value)
  }

  def notNullEncoder[A](func: A => VPackValue): VPackEncoder[A] = new VPackEncoder[A] {
    override def encode(value: A): VPackValue = Option(value).map(func).getOrElse(VPackNull)
  }

}

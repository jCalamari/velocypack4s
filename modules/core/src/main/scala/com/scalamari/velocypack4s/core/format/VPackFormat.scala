package com.scalamari.velocypack4s.core.format

import com.scalamari.velocypack4s.core.domain._

import scala.annotation.implicitNotFound

@implicitNotFound("Cannot find VPackReader for ${T}")
trait VPackReader[T] {

  def write(value: T): VPackValue

}

@implicitNotFound("Cannot find VPackWriter for ${T}")
trait VPackWriter[T] {

  def read(value: VPackValue): T

}

private[velocypack4s] trait VPackFormat[T] extends VPackReader[T] with VPackWriter[T]

private[velocypack4s] object VPackFormat {

  def apply[A](implicit enc: VPackFormat[A]): VPackFormat[A] = enc

}

private[velocypack4s] trait VPackObjectFormat[T] extends VPackFormat[T] {

  override def write(value: T): VPackObject

}

private[velocypack4s] object VPackObjectFormat {

  def apply[A](implicit enc: VPackObjectFormat[A]): VPackObjectFormat[A] = enc

  def createObjectFormat[T](rf: T => VPackObject)(wf: VPackValue => T): VPackObjectFormat[T] = new VPackObjectFormat[T] {
    override def write(value: T): VPackObject = rf(value)

    override def read(value: VPackValue): T = wf(value)
  }

}

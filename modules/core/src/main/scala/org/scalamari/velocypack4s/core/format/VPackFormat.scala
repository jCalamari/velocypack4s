package org.scalamari.velocypack4s.core.format

import org.scalamari.velocypack4s.core.domain._

import scala.annotation.implicitNotFound

@implicitNotFound("Cannot find VPackReader for ${T}")
trait VPackWriter[T] {

  def write(value: T): VPackValue

}

@implicitNotFound("Cannot find VPackWriter for ${T}")
trait VPackReader[T] {

  def read(value: VPackValue): T

}

@implicitNotFound("Cannot find VPackFormat for ${T}")
trait VPackFormat[T] extends VPackWriter[T] with VPackReader[T]

object VPackFormat {

  def apply[A](implicit enc: VPackFormat[A]): VPackFormat[A] = enc

}

@implicitNotFound("Cannot find VPackObjectFormat for ${T}")
trait VPackObjectFormat[T] extends VPackFormat[T] {

  override def write(value: T): VPackObject

}

object VPackObjectFormat {

  def apply[A](implicit enc: VPackObjectFormat[A]): VPackObjectFormat[A] = enc

}

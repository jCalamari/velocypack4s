package com.scalamari.velocypack4s.core.domain

import java.util.Date

sealed trait VPackValue

final case class VPackObject(fields: Vector[(String, VPackValue)]) extends VPackValue

final case class VPackArray(items: Vector[VPackValue]) extends VPackValue

final case class VPackString(value: String) extends VPackValue

final case class VPackNumber(value: BigDecimal) extends VPackValue

object VPackNumber {

  def apply(value: Int): VPackNumber = VPackNumber(BigDecimal(value))

  def apply(value: Byte): VPackNumber = apply(value.toInt)

  def apply(value: Short): VPackNumber = apply(value.toInt)

  def apply(value: Long): VPackNumber = VPackNumber(BigDecimal(value))

  def apply(value: Float): VPackNumber = apply(value.toDouble)

  def apply(value: Double): VPackNumber = VPackNumber(BigDecimal(value))

  def apply(value: BigInt): VPackNumber = VPackNumber(BigDecimal(value))

  def apply(value: String): VPackNumber = VPackNumber(BigDecimal(value))

  def apply(value: Array[Char]): VPackNumber = VPackNumber(BigDecimal(value))

}

final case class VPackDate(value: Date) extends VPackValue

final case class VPackBoolean(value: Boolean) extends VPackValue

case object VPackNull extends VPackValue

case object VPackEnd extends VPackValue

object VPackArray {

  def apply(fields: VPackValue*): VPackArray = VPackArray(fields.toVector)

}

object VPackObject {

  def apply(fields: (String, VPackValue)*): VPackObject = VPackObject(fields.toVector)

}

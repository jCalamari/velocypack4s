package com.scalamari.velocypack4s.core.domain

import java.util.Date

sealed trait VPackValue

final case class VPackObject(fields: Vector[(String, VPackValue)]) extends VPackValue

final case class VPackArray(items: Vector[VPackValue]) extends VPackValue

final case class VPackString(value: String) extends VPackValue

final case class VPackDouble(value: Double) extends VPackValue

final case class VPackInt(value: Int) extends VPackValue

final case class VPackFloat(value: Float) extends VPackValue

final case class VPackLong(value: Long) extends VPackValue

final case class VPackShort(value: Short) extends VPackValue

final case class VPackChar(value: Char) extends VPackValue

final case class VPackByte(value: Byte) extends VPackValue

final case class VPackDate(value: Date) extends VPackValue

final case class VPackBigDecimal(value: BigDecimal) extends VPackValue

final case class VPackBigInt(value: BigInt) extends VPackValue

final case class VPackBoolean(value: Boolean) extends VPackValue

case object VPackNull extends VPackValue

case object VPackEnd extends VPackValue

object VPackArray {

  def apply(fields: VPackValue*): VPackArray = VPackArray(fields.toVector)

}

object VPackObject {

  def apply(fields: (String, VPackValue)*): VPackObject = VPackObject(fields.toVector)

}

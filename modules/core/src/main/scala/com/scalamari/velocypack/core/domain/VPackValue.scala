package com.scalamari.velocypack.core.domain

import java.util.Date

private[velocypack] sealed trait VPackValue

private[velocypack] final case class VPackObject(fields: List[(String, VPackValue)]) extends VPackValue

private[velocypack] final case class VPackArray(items: List[VPackValue]) extends VPackValue

private[velocypack] final case class VPackString(value: String) extends VPackValue

private[velocypack] final case class VPackDouble(value: Double) extends VPackValue

private[velocypack] final case class VPackInt(value: Int) extends VPackValue

private[velocypack] final case class VPackFloat(value: Float) extends VPackValue

private[velocypack] final case class VPackLong(value: Long) extends VPackValue

private[velocypack] final case class VPackShort(value: Short) extends VPackValue

private[velocypack] final case class VPackChar(value: Char) extends VPackValue

private[velocypack] final case class VPackByte(value: Byte) extends VPackValue

private[velocypack] final case class VPackDate(value: Date) extends VPackValue

private[velocypack] final case class VPackBigDecimal(value: BigDecimal) extends VPackValue

private[velocypack] final case class VPackBigInt(value: BigInt) extends VPackValue

private[velocypack] final case class VPackBoolean(value: Boolean) extends VPackValue

private[velocypack] case object VPackNull extends VPackValue

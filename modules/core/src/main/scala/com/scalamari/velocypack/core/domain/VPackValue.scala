package com.scalamari.velocypack.core.domain

private[velocypack] sealed trait VPackValue

private[velocypack] final case class VPackObject(fields: List[(String, VPackValue)]) extends
  VPackValue

private[velocypack] final case class VPackArray(items: List[VPackValue]) extends VPackValue

private[velocypack] final case class VPackString(value: String) extends VPackValue

private[velocypack] final case class VPackNumber(value: Double) extends VPackValue

private[velocypack] final case class VPackBoolean(value: Boolean) extends VPackValue

private[velocypack] case object VPackNull extends VPackValue

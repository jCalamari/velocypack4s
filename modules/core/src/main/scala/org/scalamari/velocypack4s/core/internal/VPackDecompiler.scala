package org.scalamari.velocypack4s.core.internal

import com.arangodb.velocypack.VPackSlice
import org.scalamari.velocypack4s.core.domain._
import org.scalamari.velocypack4s.core.unsupportedFieldError

import scala.annotation.tailrec
import scala.collection.JavaConverters._

private[core] trait VPackDecompiler {

  def fromSlice(value: VPackSlice): VPackValue = {
    decompileInternal(Vector(value), Vector.empty).head
  }

  @tailrec
  private def decompileInternal(fields: Vector[VPackSlice], state: Vector[VPackValue]): Vector[VPackValue] = fields match {
    case head +: tail => decompileInternal(tail, state :+ fromSliceInternal(head))
    case IndexedSeq() => state
  }

  private def fromSliceInternal(value: VPackSlice): VPackValue = {
    if (value.isObject) {
      fromObject(value.objectIterator().asScala.map(entry => entry.getKey -> entry.getValue).toVector)
    } else if (value.isArray) {
      fromArray(value.arrayIterator().asScala.toVector)
    } else {
      fromPrimitive(value)
    }
  }

  private def fromObject(fields: Vector[(String, VPackSlice)]): VPackObject = {
    VPackObject(fields.map { case (key, value) => key -> fromSlice(value) })
  }

  private def fromArray(fields: Vector[VPackSlice]): VPackArray = {
    VPackArray(fields.map(fromSlice))
  }

  private def fromPrimitive(field: VPackSlice): VPackValue = {
    if (field.isNumber) {
      VPackNumber(field.getAsBigDecimal)
    } else if (field.isString) {
      VPackString(field.getAsString)
    } else if (field.isDate) {
      VPackDate(field.getAsDate)
    } else if (field.isBoolean) {
      VPackBoolean(field.getAsBoolean)
    } else if (field.isNull || field.isNone) {
      VPackNull
    } else unsupportedFieldError(s"[$field]")
  }

}

private[core] object VPackDecompiler extends VPackDecompiler

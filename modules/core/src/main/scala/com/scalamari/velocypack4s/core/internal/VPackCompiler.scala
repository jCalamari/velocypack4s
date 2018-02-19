package com.scalamari.velocypack4s.core.internal

import com.arangodb.velocypack.{VPackBuilder, VPackSlice, ValueType}
import com.scalamari.velocypack4s.core.domain._

import scala.annotation.tailrec

private[core] trait VPackCompiler {

  private val nullKey: String = null

  def toSlice(value: VPackValue): VPackSlice = {
    val builder = new VPackBuilder()
    toSlice(value, builder, null)
    builder.slice()
  }

  def toSlice(value: VPackValue, builder: VPackBuilder, attribute: String): Unit = {
    compileInternal(Vector(attribute -> value), builder)
  }

  @tailrec
  private def compileInternal(values: Vector[(String, VPackValue)], state: VPackBuilder): Unit = values match {
    case (attribute, head) +: tail =>
      head match {
        case VPackObject(fields) => compileInternal(addEndValue(fields) ++ tail, state.add(attribute, ValueType.OBJECT))
        case VPackArray(fields)  => compileInternal(addEndValue(addNullKeys(fields)) ++ tail, state.add(attribute, ValueType.ARRAY))
        case VPackString(value)  => compileInternal(tail, state.add(attribute, value))
        case VPackNumber(value)  => compileInternal(tail, state.add(attribute, value.bigDecimal))
        case VPackDate(value)    => compileInternal(tail, state.add(attribute, value))
        case VPackBoolean(value) => compileInternal(tail, state.add(attribute, value))
        case VPackNull           => compileInternal(tail, state.add(attribute, ValueType.NULL))
        case VPackEnd            => compileInternal(tail, state.close())
      }
    case IndexedSeq() =>
  }

  private def addNullKeys(fields: Vector[VPackValue]): Vector[(String, VPackValue)] = fields.map(value => nullKey -> value)

  private def addEndValue(fields: Vector[(String, VPackValue)]): Vector[(String, VPackValue)] = fields :+ (nullKey, VPackEnd)

}

private[core] object VPackCompiler extends VPackCompiler

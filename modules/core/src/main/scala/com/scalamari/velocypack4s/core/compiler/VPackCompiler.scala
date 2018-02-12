package com.scalamari.velocypack4s.core.compiler

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
        case VPackObject(fields)    => compileInternal(addEndValue(fields) ++ tail, state.add(attribute, ValueType.OBJECT))
        case VPackArray(fields)     => compileInternal(addEndValue(addNullKeys(fields)) ++ tail, state.add(attribute, ValueType.ARRAY))
        case VPackString(value)     => compileInternal(tail, state.add(attribute, value))
        case VPackDouble(value)     => compileInternal(tail, state.add(attribute, value))
        case VPackInt(value)        => compileInternal(tail, state.add(attribute, value: java.lang.Integer))
        case VPackFloat(value)      => compileInternal(tail, state.add(attribute, value: java.lang.Float))
        case VPackLong(value)       => compileInternal(tail, state.add(attribute, value: java.lang.Long))
        case VPackShort(value)      => compileInternal(tail, state.add(attribute, value: java.lang.Short))
        case VPackChar(value)       => compileInternal(tail, state.add(attribute, value: java.lang.Character))
        case VPackByte(value)       => compileInternal(tail, state.add(attribute, value: java.lang.Byte))
        case VPackDate(value)       => compileInternal(tail, state.add(attribute, value))
        case VPackBigDecimal(value) => compileInternal(tail, state.add(attribute, value.bigDecimal))
        case VPackBigInt(value)     => compileInternal(tail, state.add(attribute, value.bigInteger))
        case VPackBoolean(value)    => compileInternal(tail, state.add(attribute, value))
        case VPackNull              => compileInternal(tail, state.add(attribute, ValueType.NULL))
        case VPackEnd               => compileInternal(tail, state.close())
      }
    case IndexedSeq() =>
  }

  private def addNullKeys(fields: Vector[VPackValue]): Vector[(String, VPackValue)] = fields.map(value => nullKey -> value)

  private def addEndValue(fields: Vector[(String, VPackValue)]): Vector[(String, VPackValue)] = fields :+ (nullKey, VPackEnd)

}

private[core] object VPackCompiler extends VPackCompiler

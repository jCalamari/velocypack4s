package com.scalamari.velocypack4s.core.compiler

import com.arangodb.velocypack.{VPackBuilder, VPackSlice, ValueType}
import com.scalamari.velocypack4s.core.domain._

private[velocypack4s] trait VPackCompiler {

  def toSlice(value: VPackValue): VPackSlice = {
    val state = new VPackBuilder()
    compileInternal(value, state)
    state.slice()
  }

  private def compileInternal(value: VPackValue, state: VPackBuilder, fieldName: String = null): Unit = value match {
    case v: VPackObject =>
      state.add(fieldName, ValueType.OBJECT)
      v.fields.foreach { case (name, field) => compileInternal(field, state, name) }
      state.close()
    case v: VPackArray =>
      state.add(ValueType.ARRAY)
      v.items.foreach(item => compileInternal(item, state))
      state.close()
    case v: VPackString     => state.add(fieldName, v.value)
    case v: VPackDouble     => state.add(fieldName, v.value)
    case v: VPackInt        => state.add(fieldName, v.value: java.lang.Integer)
    case v: VPackFloat      => state.add(fieldName, v.value: java.lang.Float)
    case v: VPackLong       => state.add(fieldName, v.value: java.lang.Long)
    case v: VPackShort      => state.add(fieldName, v.value: java.lang.Short)
    case v: VPackChar       => state.add(fieldName, v.value: java.lang.Character)
    case v: VPackByte       => state.add(fieldName, v.value: java.lang.Byte)
    case v: VPackDate       => state.add(fieldName, v.value)
    case v: VPackBigDecimal => state.add(fieldName, v.value.bigDecimal)
    case v: VPackBigInt     => state.add(fieldName, v.value.bigInteger)
    case v: VPackBoolean    => state.add(fieldName, v.value)
    case _: VPackNull.type  => state.add(fieldName, ValueType.NULL)
  }

}

private[velocypack4s] object VPackCompiler extends VPackCompiler

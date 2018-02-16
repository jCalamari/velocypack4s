package com.scalamari.velocypack4s.core.format

import java.util.Date

import com.scalamari.velocypack4s.core.deserializationError
import com.scalamari.velocypack4s.core.domain._

private[core] trait BasicFormats {

  implicit def VPackFormat[T <: VPackValue]: VPackFormat[T] = new VPackFormat[T] {

    override def write(value: T): VPackValue = value

    override def read(value: VPackValue): T = value.asInstanceOf[T]
  }

  implicit object StringFormat extends VPackFormat[String] {
    override def write(value: String): VPackValue = VPackString(value)

    override def read(value: VPackValue): String = value match {
      case VPackString(str) => str
      case _                => deserializationError(s"StringFormat: Not a String: [$value]")
    }
  }

  implicit object DoubleFormat extends VPackFormat[Double] {
    override def write(value: Double): VPackValue = VPackDouble(value)

    override def read(value: VPackValue): Double = value match {
      case VPackDouble(double) => double
      case _                   => deserializationError(s"DoubleFormat: Not a Double: [$value]")
    }
  }

  implicit object IntFormat extends VPackFormat[Int] {
    override def write(value: Int): VPackValue = VPackInt(value)

    override def read(value: VPackValue): Int = value match {
      case VPackInt(int) => int
      case _             => deserializationError(s"IntFormat: Not an Int: [$value]")
    }
  }

  implicit object FloatFormat extends VPackFormat[Float] {
    override def write(value: Float): VPackValue = VPackFloat(value)

    override def read(value: VPackValue): Float = value match {
      case VPackFloat(flaot) => flaot
      case _                 => deserializationError(s"FloatFormat: Not an Float: [$value]")
    }
  }

  implicit object LongFormat extends VPackFormat[Long] {
    override def write(value: Long): VPackValue = VPackLong(value)

    override def read(value: VPackValue): Long = value match {
      case VPackLong(long) => long
      case _               => deserializationError(s"LongFormat: Not an Long: [$value]")
    }
  }

  implicit object ShortFormat extends VPackFormat[Short] {
    override def write(value: Short): VPackValue = VPackShort(value)

    override def read(value: VPackValue): Short = value match {
      case VPackShort(str) => str
      case _               => deserializationError(s"ShortFormat: Not an Short: [$value]")
    }
  }

  implicit object CharFormat extends VPackFormat[Char] {
    override def write(value: Char): VPackValue = VPackChar(value)

    override def read(value: VPackValue): Char = value match {
      case VPackChar(char) => char
      case _               => deserializationError(s"CharFormat: Not an Char: [$value]")
    }
  }

  implicit object ByteFormat extends VPackFormat[Byte] {
    override def write(value: Byte): VPackValue = VPackByte(value)

    override def read(value: VPackValue): Byte = value match {
      case VPackByte(byte) => byte
      case _               => deserializationError(s"ByteFormat: Not an Byte: [$value]")
    }
  }

  implicit object DateFormat extends VPackFormat[Date] {
    override def write(value: Date): VPackValue = VPackDate(value)

    override def read(value: VPackValue): Date = value match {
      case VPackDate(date) => date
      case _               => deserializationError(s"DateFormat: Not an Date: [$value]")
    }
  }

  implicit object BigDecimalFormat extends VPackFormat[BigDecimal] {
    override def write(value: BigDecimal): VPackValue = VPackBigDecimal(value)

    override def read(value: VPackValue): BigDecimal = value match {
      case VPackBigDecimal(dec) => dec
      case _                    => deserializationError(s"BigDecimalFormat: Not an BigDecimal: [$value]")
    }
  }

  implicit object BigIntFormat extends VPackFormat[BigInt] {
    override def write(value: BigInt): VPackValue = VPackBigInt(value)

    override def read(value: VPackValue): BigInt = value match {
      case VPackBigInt(int) => int
      case _                => deserializationError(s"BigIntFormat: Not an BigInt: [$value]")
    }
  }

  implicit object BooleanFormat extends VPackFormat[Boolean] {
    override def write(value: Boolean): VPackValue = VPackBoolean(value)

    override def read(value: VPackValue): Boolean = value match {
      case VPackBoolean(bool) => bool
      case _                  => deserializationError(s"BooleanFormat: Not an BigDecimal: [$value]")
    }
  }

}

private[core] object BasicFormats extends BasicFormats

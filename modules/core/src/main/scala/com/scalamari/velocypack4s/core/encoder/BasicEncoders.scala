package com.scalamari.velocypack4s.core.encoder

import java.util.Date

import com.scalamari.velocypack4s.core.domain._

private[velocypack4s] trait BasicEncoders {

  implicit val stringEncoder:     VPackEncoder[String]     = VPackEncoder.createEncoder(VPackString)
  implicit val doubleEncoder:     VPackEncoder[Double]     = VPackEncoder.createEncoder(VPackDouble)
  implicit val intEncoder:        VPackEncoder[Int]        = VPackEncoder.createEncoder(VPackInt)
  implicit val floatEncoder:      VPackEncoder[Float]      = VPackEncoder.createEncoder(VPackFloat)
  implicit val longEncoder:       VPackEncoder[Long]       = VPackEncoder.createEncoder(VPackLong)
  implicit val shortEncoder:      VPackEncoder[Short]      = VPackEncoder.createEncoder(VPackShort)
  implicit val charEncoder:       VPackEncoder[Char]       = VPackEncoder.createEncoder(VPackChar)
  implicit val byteEncoder:       VPackEncoder[Byte]       = VPackEncoder.createEncoder(VPackByte)
  implicit val dateEncoder:       VPackEncoder[Date]       = VPackEncoder.notNullEncoder(VPackDate)
  implicit val bigDecimalEncoder: VPackEncoder[BigDecimal] = VPackEncoder.createEncoder(VPackBigDecimal)
  implicit val bigIntEncoder:     VPackEncoder[BigInt]     = VPackEncoder.createEncoder(VPackBigInt)
  implicit val booleanEncoder:    VPackEncoder[Boolean]    = VPackEncoder.createEncoder(VPackBoolean)

}

private[velocypack4s] object BasicEncoders extends BasicEncoders

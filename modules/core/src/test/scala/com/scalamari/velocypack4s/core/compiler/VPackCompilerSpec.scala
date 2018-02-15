package com.scalamari.velocypack4s.core.compiler

import java.util.Date

import com.scalamari.velocypack4s.core.RichT
import com.scalamari.velocypack4s.core.domain._
import com.scalamari.velocypack4s.core.format.BasicFormats._
import org.scalatest.{Matchers, WordSpec}

import scala.collection.JavaConverters._

class VPackCompilerSpec extends WordSpec with Matchers {

  "VPackCompiler" should {

    "compile primitive types" in {
      val dateValue = new Date
      VPackCompiler.toSlice(VPackString("string")).getAsString shouldBe "string"
      VPackCompiler.toSlice(VPackDouble(1.0)).getAsDouble shouldBe 1.0
      VPackCompiler.toSlice(VPackInt(1)).getAsInt shouldBe 1
      VPackCompiler.toSlice(VPackFloat(1F)).getAsFloat shouldBe 1F
      VPackCompiler.toSlice(VPackLong(1L)).getAsLong shouldBe 1L
      VPackCompiler.toSlice(VPackShort(1)).getAsShort shouldBe 1
      VPackCompiler.toSlice(VPackChar('c')).getAsChar shouldBe 'c'
      VPackCompiler.toSlice(VPackByte(1)).getAsByte shouldBe 1.byteValue()
      VPackCompiler.toSlice(VPackDate(dateValue)).getAsDate shouldBe dateValue
      VPackCompiler.toSlice(VPackBigDecimal(BigDecimal(1.0))).getAsBigDecimal shouldBe BigDecimal(1.0).bigDecimal
      VPackCompiler.toSlice(VPackBigInt(BigInt(1))).getAsBigInteger shouldBe BigInt(1).bigInteger
      VPackCompiler.toSlice(VPackBoolean(true)).getAsBoolean shouldBe true
      VPackCompiler.toSlice(VPackNull)
    }

    "compile object" in {
      val instance = VPackObject("bar" -> "bar".toVPack, "baz" -> true.toVPack)
      val encoded = VPackCompiler.toSlice(VPackArray(instance))
      encoded.isArray shouldBe true
      val items = encoded.arrayIterator().asScala.toList
      items should not be empty
      items.head.isObject shouldBe true
    }

    case class Foo(bar: String, baz: Boolean)

  }

}

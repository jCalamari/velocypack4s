package com.scalamari.velocypack4s.core.compiler

import java.util.Date

import com.scalamari.velocypack4s.core.RichAny
import com.scalamari.velocypack4s.core.domain._
import com.scalamari.velocypack4s.core.format.BasicFormats._
import org.scalatest.{Matchers, WordSpec}

import scala.collection.JavaConverters._

class VPackCompilerSpec extends WordSpec with Matchers {

  "VPackCompiler" should {

    "compile primitive types" in {
      val dateValue = new Date
      VPackCompiler.toSlice(VPackString("string")).getAsString shouldBe "string"
      VPackCompiler.toSlice(VPackNumber(1)).getAsInt shouldBe 1.intValue()
      VPackCompiler.toSlice(VPackDate(dateValue)).getAsDate shouldBe dateValue
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

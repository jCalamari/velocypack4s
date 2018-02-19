package com.scalamari.velocypack4s.core.internal

import java.util.Date

import com.arangodb.velocypack.{VPackBuilder, ValueType}
import com.scalamari.velocypack4s.core.RichAny
import com.scalamari.velocypack4s.core.domain._
import com.scalamari.velocypack4s.core.format.BasicFormats._
import org.scalatest.{Matchers, WordSpec}

class VPackCompilerSpec extends WordSpec with Matchers {

  "VPackCompiler" should {

    "compile primitive types" in {
      checkRoundTrip(VPackString("string"))
      checkRoundTrip(VPackNumber(1))
      checkRoundTrip(VPackNumber(Array('0')))
      checkRoundTrip(VPackNumber("1"))
      checkRoundTrip(VPackDate(new Date))
      checkRoundTrip(VPackBoolean(true))
      checkRoundTrip(VPackNull)
    }

    "compile an object and array" in {
      val instance = VPackObject("bar" -> "bar".toVPack, "baz" -> true.toVPack)
      checkRoundTrip(instance)
      checkRoundTrip(VPackArray(instance))
    }

    "compile none & null" in {
      VPackDecompiler.fromSlice(new VPackBuilder().slice()) shouldBe VPackNull
      VPackDecompiler.fromSlice(new VPackBuilder().add(ValueType.NULL).slice()) shouldBe VPackNull
    }

    case class Foo(bar: String, baz: Boolean)

  }

  private def checkRoundTrip(value: VPackValue): Unit = {
    VPackDecompiler.fromSlice(VPackCompiler.toSlice(value)) shouldBe value
  }

}

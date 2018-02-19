package com.scalamari.velocypack4s.macros

import java.util.Date

import com.scalamari.velocypack4s.core._
import com.scalamari.velocypack4s.core.domain.{VPackArray, VPackObject, VPackString}
import com.scalamari.velocypack4s.core.format.VPackFormat
import org.scalatest.{Matchers, WordSpec}

class MacroFormatsSpec extends WordSpec with Matchers {

  "MacroFormats" should {

    "format a foo bar" in {
      val instances = List(Foo.instance)
      implicit val format: VPackFormat[Foo] = vpackFormat[Foo]
      val encoded = instances.toVPack
      encoded shouldBe a[VPackArray]
      val items = encoded.asInstanceOf[VPackArray].items
      items should not be empty
      val head = items.head
      head shouldBe a[VPackObject]
      val obj = head.asInstanceOf[VPackObject]
      val fields = obj.fields.toMap
      fields.get("bar") shouldBe Some(VPackString("bar"))
    }

    "round-trip a case class" in {
      checkRoundTrip(Foo.instance)
    }

    "compile only a case class" in {
      "vpackFormat[ScalaClass]" shouldNot compile
      "vpackFormat[CaseClass]" should compile
      "vpackFormat[String]" shouldNot compile
    }

    "fail to read a non-object" in {
      a[DeserializationException] shouldBe thrownBy {
        vpackFormat[Foo].read(VPackString("str"))
      }
    }

  }

  class ScalaClass

  case class CaseClass()

  private def checkRoundTrip[A](value: A)(implicit f: VPackFormat[A]) = {
    f.read(f.write(value)) shouldBe value
  }

  case class Foo(
    bar: String,
    baz: Boolean,
    qux: Int,
    quux: Double,
    corge: Float,
    grault: Long,
    garply: Short,
    waldo: Char,
    fred: BigDecimal,
    plugh: BigInt,
    xyzzy: Date,
    thud: Byte
  )

  object Foo {

    def instance: Foo = Foo(
      bar = "bar",
      baz = true,
      qux = Int.MaxValue,
      quux = Double.MaxValue,
      corge = Float.MinValue,
      grault = Long.MaxValue,
      garply = Short.MinValue,
      waldo = Char.MaxValue,
      fred = BigDecimal(Double.MinValue),
      plugh = BigInt(Int.MaxValue),
      xyzzy = nullDate,
      thud = 255.toByte
    )

    private val nullDate: Date = null

  }

}

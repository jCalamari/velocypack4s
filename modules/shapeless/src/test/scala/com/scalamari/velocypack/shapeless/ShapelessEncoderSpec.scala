package com.scalamari.velocypack.shapeless

import java.util.Date

import com.scalamari.velocypack.core.domain.VPackArray
import com.scalamari.velocypack.core.encoder.VPackEncoder
import org.scalatest.{Matchers, WordSpec}

class ShapelessEncoderSpec extends WordSpec with Matchers {

  "ShapelessEncoder" should {

    "encode foo bar" in {

      val instances = List(Foo.instance)
      val encoded = VPackEncoder[List[Foo]].encode(instances)
      encoded shouldBe a[VPackArray]
      encoded.asInstanceOf[VPackArray].items should not be empty

    }
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

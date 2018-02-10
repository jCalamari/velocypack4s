package com.scalamari.velocypack.shapeless

import com.scalamari.velocypack.core.domain.VPackObject
import com.scalamari.velocypack.core.encoder.VPackEncoder
import org.scalatest.{Matchers, WordSpec}

class ShapelessEncoderSpec extends WordSpec with Matchers {

  "ShapelessEncoder" should {

    "encode foo bar" in {

      val instance = Foo("bar", baz = true)
      val encoded = VPackEncoder[Foo].encode(instance)
      encoded shouldBe a[VPackObject]

    }
  }

  case class Foo(bar: String, baz: Boolean)

}

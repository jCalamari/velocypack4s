package com.scalamari.velocypack4s.shapeless

import com.arangodb.velocypack.VPack
import com.scalamari.velocypack4s.core._
import org.scalatest.{Matchers, WordSpec}

class SerializerSpec extends WordSpec with Matchers {

  "Serializer" should {

    "serialize a case class" in {

      val vpack = new VPack.Builder().registerSerializer[Baz](classOf[Baz], serializer[Baz]).build()
      val slice = vpack.serialize(Baz("bar", qux = true))
      slice.isObject shouldBe true
      slice.get("foo").getAsString shouldBe "bar"
      slice.get("qux").getAsBoolean shouldBe true

    }

  }

  case class Baz(foo: String, qux: Boolean)

}

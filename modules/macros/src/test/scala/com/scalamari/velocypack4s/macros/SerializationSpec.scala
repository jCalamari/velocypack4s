package com.scalamari.velocypack4s.macros

import com.arangodb.velocypack.VPack
import com.scalamari.velocypack4s.core._
import com.scalamari.velocypack4s.core.format.VPackFormat
import org.scalatest.{Matchers, WordSpec}

import scala.reflect.ClassTag

class SerializationSpec extends WordSpec with Matchers {

  "Serializer" should {

    "round-trip a case class" in {
      checkRoundTrip(Baz("bar", qux = true))
    }

  }

  private def checkRoundTrip[A: VPackFormat](value: A)(implicit ct: ClassTag[A]): Unit = {
    val clazz = ct.runtimeClass.asInstanceOf[Class[A]]

    val vpack = new VPack.Builder()
      .registerSerializer[A](clazz, serializer[A])
      .registerDeserializer[A](clazz, deserializer[A])
      .build()

    vpack.deserialize[A](vpack.serialize(value), clazz) shouldBe value
  }

  case class Baz(foo: String, qux: Boolean)

}

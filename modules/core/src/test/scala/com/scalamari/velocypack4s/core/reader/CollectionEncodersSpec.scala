package com.scalamari.velocypack4s.core.reader

import com.scalamari.velocypack4s.core._
import com.scalamari.velocypack4s.core.domain._
import org.scalatest.{Matchers, WordSpec}

import scala.collection.mutable.ListBuffer

class CollectionEncodersSpec extends WordSpec with Matchers {

  "CollectionEncoders" should {

    "encode collections" in {
      val immutable = List("value")
      assertIsArray(immutable.toVPack)
      assertIsArray(immutable.toArray.toVPack)
      assertIsArray(immutable.toVector.toVPack)
      assertIsArray(immutable.toSet.toVPack)
      assertIsArray(immutable.toSeq.toVPack)
      assertIsArray(immutable.toIndexedSeq.toVPack)
      assertIsArray(immutable.toIterable.toVPack)
      // assertIsArray(list.headOption.toVPack)

      val mutable = ListBuffer("value")
      assertIsArray(mutable.toArray.toVPack)
      assertIsArray(mutable.toArray.toVPack)
      assertIsArray(mutable.toSet.toVPack)
      assertIsArray(mutable.toSeq.toVPack)
      assertIsArray(mutable.toIndexedSeq.toVPack)
      assertIsArray(mutable.toIterable.toVPack)
    }

    "encode option" in {
      val value = Option("some")
      value.toVPack shouldBe VPackString("some")
      (None: Option[String]).toVPack shouldBe VPackNull
    }

    "encode map" in {
      val value: Map[String, Int] = Map("key" -> 1)
      value.toVPack shouldBe VPackObject("key" -> VPackInt(1))

      a[SerializationException] should be thrownBy {
        Map(1 -> 1).toVPack
      }
    }
  }

  private def assertIsArray(value: VPackValue): Unit = value match {
    case VPackArray(Vector(VPackString("value"))) =>
    case other => fail(s"No a list: [$other]")
  }

}

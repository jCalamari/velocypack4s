package com.scalamari.velocypack4s.core.format

import com.scalamari.velocypack4s.core._
import com.scalamari.velocypack4s.core.domain._
import org.scalatest.{Matchers, WordSpec}

import scala.collection.mutable.ListBuffer

class CollectionFormatsSpec extends WordSpec with Matchers {

  "CollectionFormats" should {

    "format collections" in {
      val immutable = List("value")
      checkRoundTrip(immutable)
      checkRoundTrip(immutable.toArray)
      checkRoundTrip(immutable.toVector)
      checkRoundTrip(immutable.toSet)
      checkRoundTrip(immutable.toSeq)
      checkRoundTrip(immutable.toIndexedSeq)
      checkRoundTrip(immutable.toIterable)

      val mutable = ListBuffer("value")
      checkRoundTrip(mutable.toArray)
      checkRoundTrip(mutable.toArray)
      checkRoundTrip(mutable.toSet)
      checkRoundTrip(mutable.toSeq)
      checkRoundTrip(mutable.toIndexedSeq)
      checkRoundTrip(mutable.toIterable)
    }

    "format option" in {
      checkRoundTrip(Option("some"))
      checkRoundTrip(None: Option[Int])
      checkRoundTrip(VPackNull)
      checkRoundTrip(VPackString("maybe"))
    }

    "format map" in {
      checkRoundTrip(Map("key" -> 1))
    }
  }

  // TODO DRY
  private def checkRoundTrip[A](value: A)(implicit format: VPackFormat[A]): Unit = {
    format.read(format.write(value)) shouldBe value
  }

}

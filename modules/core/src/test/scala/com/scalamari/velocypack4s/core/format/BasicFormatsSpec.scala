package com.scalamari.velocypack4s.core.format

import com.scalamari.velocypack4s.core.DeserializationException
import com.scalamari.velocypack4s.core.domain.VPackNull
import com.scalamari.velocypack4s.core.format.BasicFormats._
import org.scalatest.{Matchers, WordSpec}

class BasicFormatsSpec extends WordSpec with Matchers {

  "BasicFormats" should {

    "fail to read invalid vpack value" in {
      Seq(
        StringFormat,
        DoubleFormat,
        IntFormat,
        FloatFormat,
        LongFormat,
        ShortFormat,
        CharFormat,
        ByteFormat,
        DateFormat,
        BigDecimalFormat,
        BigIntFormat,
        BooleanFormat
      ) foreach { format =>
        a[DeserializationException] shouldBe thrownBy {
          format.read(VPackNull)
        }
      }
    }

  }

}

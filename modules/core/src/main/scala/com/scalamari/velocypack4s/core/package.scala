package com.scalamari.velocypack4s

import com.scalamari.velocypack4s.core.domain._
import com.scalamari.velocypack4s.core.encoder._

package object core extends BasicEncoders with CollectionEncoders {

  class EncodingException(message: String) extends RuntimeException(message)

  private[velocypack4s] implicit class RichVPackValue[T](value: T) {
    def toVPack(implicit enc: VPackEncoder[T]): VPackValue = enc.encode(value)
  }

}
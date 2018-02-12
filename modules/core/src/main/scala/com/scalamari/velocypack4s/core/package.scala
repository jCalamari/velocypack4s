package com.scalamari.velocypack4s

import com.arangodb.velocypack.{VPackBuilder, VPackSerializationContext, VPackSerializer}
import com.scalamari.velocypack4s.core.compiler.VPackCompiler
import com.scalamari.velocypack4s.core.domain._
import com.scalamari.velocypack4s.core.encoder._

package object core extends BasicEncoders with CollectionEncoders {

  def serializer[A](implicit enc: VPackEncoder[A]): VPackSerializer[A] = new VPackSerializer[A] {
    override def serialize(builder: VPackBuilder, attribute: String, value: A, context: VPackSerializationContext): Unit = {
      VPackCompiler.toSlice(enc.encode(value), builder, attribute)
    }
  }

  class EncodingException(message: String) extends RuntimeException(message)

  private[core] implicit class RichVPackValue[T](value: T) {
    def toVPack(implicit enc: VPackEncoder[T]): VPackValue = enc.encode(value)
  }

}
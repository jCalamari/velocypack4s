package com.scalamari.velocypack4s

import com.arangodb.velocypack._
import com.scalamari.velocypack4s.core.domain._
import com.scalamari.velocypack4s.core.format._
import com.scalamari.velocypack4s.core.internal.{VPackCompiler, VPackDecompiler}

package object core extends BasicFormats with CollectionFormats {

  def serializer[A](implicit writer: VPackWriter[A]): VPackSerializer[A] = new VPackSerializer[A] {
    override def serialize(builder: VPackBuilder, attribute: String, value: A, context: VPackSerializationContext): Unit = {
      VPackCompiler.toSlice(writer.write(value), builder, attribute)
    }
  }

  def deserializer[A](implicit reader: VPackReader[A]): VPackDeserializer[A] = new VPackDeserializer[A] {
    override def deserialize(parent: VPackSlice, vpack: VPackSlice, context: VPackDeserializationContext): A = {
      reader.read(VPackDecompiler.fromSlice(vpack))
    }
  }

  class UnsupportedFieldException(message: String) extends RuntimeException(message)

  class SerializationException(message: String) extends RuntimeException(message)

  class DeserializationException(message: String) extends RuntimeException(message)

  def serializationError(message: String) = throw new SerializationException(message)

  def deserializationError(message: String) = throw new DeserializationException(message)

  def unsupportedFieldError(message: String) = throw new UnsupportedFieldException(message)

  implicit class RichAny[T](value: T) {
    def toVPack(implicit ev: VPackWriter[T]): VPackValue = ev.write(value)
  }

  implicit class RichVPackValue(value: VPackValue) {
    def convertTo[T](implicit ev: VPackReader[T]): T = ev.read(value)
  }

}

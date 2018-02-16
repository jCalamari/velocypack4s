package com.scalamari.velocypack4s

import com.scalamari.velocypack4s.core.format.VPackFormat

import scala.language.experimental.{macros => m}

package object macros {

  implicit def materializeVPackFormat[T] = macro Macros.materializeFormatImpl[T]

  def vpackFormat[T]: VPackFormat[T] = macro Macros.materializeFormatImpl[T]

}

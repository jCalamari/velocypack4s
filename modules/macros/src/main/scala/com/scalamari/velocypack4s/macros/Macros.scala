package org.scalamari.velocypack4s.macros

import org.scalamari.velocypack4s.core.format.VPackFormat

import scala.reflect.macros.blackbox

private[macros] object Macros {

  def materializeFormatImpl[T: c.WeakTypeTag](c: blackbox.Context): c.Expr[VPackFormat[T]] = {
    import c.universe._

    def assertCaseClass(tpe: c.universe.Type): Unit = {
      val symbol = tpe.typeSymbol
      if (!symbol.isClass || !symbol.asClass.isCaseClass) {
        c.abort(c.enclosingPosition, s"${symbol.fullName} must be a case class")
      }
    }

    def getCaseClassFields(tpe: c.universe.Type): Seq[c.universe.Symbol] = {
      tpe.decls.collectFirst {
        case m: MethodSymbol if m.isPrimaryConstructor => m
      }.get.paramLists.head
    }

    val tpe = weakTypeOf[T]
    assertCaseClass(tpe)

    val fields = getCaseClassFields(tpe).toVector

    val vpackFields = fields.map { field =>
      val name = field.name
      val key = name.decodedName.toString
      q"$key -> t.${name.toTermName}.toVPack"
    }

    val classFields = fields.map { field =>
      val name = field.name
      val decoded = name.decodedName.toString
      val returnType = tpe.decl(name).typeSignature
      q"f($decoded).convertTo[$returnType]"
    }

    val companion = tpe.typeSymbol.companion

    c.Expr[VPackFormat[T]] {
      q"""
      new org.scalamari.velocypack4s.core.format.VPackFormat[$tpe] {
        override def read(t: org.scalamari.velocypack4s.core.domain.VPackValue): $tpe = t match {
          case o: org.scalamari.velocypack4s.core.domain.VPackObject => readObject(o)
          case _ => org.scalamari.velocypack4s.core.deserializationError(s"Not an object: [$$t]")
        }

        private def readObject(o: org.scalamari.velocypack4s.core.domain.VPackObject): $tpe = {
          val f = o.fields.toMap
          $companion(..$classFields)
        }

        override def write(t: $tpe): org.scalamari.velocypack4s.core.domain.VPackValue = {
          org.scalamari.velocypack4s.core.domain.VPackObject(..$vpackFields)
        }
      }
      """
    }
  }
}

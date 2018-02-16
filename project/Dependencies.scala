import sbt.Keys._
import sbt._

object Dependencies {

  object Versions {
    val velocypack   = "1.0.14"
    val scalatest    = "3.0.4"
    val scalaReflect = "2.12.4"
  }

  object Libraries {
    val velocypack   = "com.arangodb"   % "velocypack"     % Versions.velocypack
    val scalatest    = "org.scalatest"  % "scalatest_2.12" % Versions.scalatest
    val scalaReflect = "org.scala-lang" % "scala-reflect"  % Versions.scalaReflect
  }

  val l = libraryDependencies

  val core = l ++= Seq(
    Libraries.velocypack,
    Libraries.scalatest % Test
  )

  val macros = l ++= Seq(
    Libraries.scalaReflect,
    Libraries.scalatest % Test
  )

}

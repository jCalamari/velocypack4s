import sbt.Keys.libraryDependencies
import sbt._

object Dependencies {

  object Versions {
    val velocypack = "1.0.14"
    val shapeless  = "2.3.3"
    val scalatest  = "3.0.4"
  }

  object Libraries {
    val velocypack = "com.arangodb"  % "velocypack"     % Versions.velocypack
    val shapeless  = "com.chuusai"   %% "shapeless"     % Versions.shapeless
    val scalatest  = "org.scalatest" % "scalatest_2.12" % Versions.scalatest
  }

  val l = libraryDependencies

  val core = l ++= Seq(
    Libraries.velocypack,
    Libraries.scalatest % Test
  )

  val shapeless = l ++= Seq(
    Libraries.shapeless,
    Libraries.scalatest % Test
  )

}

import sbt.file
import tut.TutPlugin.autoImport.tutTargetDirectory

object Tut {

  lazy val settings = Seq(
    tutTargetDirectory := file(".")
  )

}

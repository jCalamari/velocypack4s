import sbt.file
import tut.TutPlugin.autoImport.tutTargetDirectory

object Tut {

  def settings = Seq(
    tutTargetDirectory := file(".")
  )

}

import com.typesafe.sbt.SbtGit.git

import scala.util.matching.Regex

object Git {

  private val ReleaseTag: Regex = """^v([\d\.]+)$""".r

  lazy val settings = Seq(
    git.baseVersion := "1.0.0",
    git.gitTagToVersionNumber := {
      case ReleaseTag(v) => Some(v)
      case _ => None
    },
    git.formattedShaVersion := {
      val suffix = git.makeUncommittedSignifierSuffix(git.gitUncommittedChanges.value, git.uncommittedSignifier.value)
      git.gitHeadCommit.value map {
        _.substring(0, 7)
      } map { sha =>
        git.baseVersion.value + "-" + sha + suffix
      }
    })

}

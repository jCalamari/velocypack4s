import sbt.Keys._
import sbt._
import xerial.sbt.Sonatype.autoImport._

object Release {

  lazy val settings = Seq(
    sonatypeProfileName := organization.value,
    homepage := Some(url("https://github.com/jCalamari/velocypack4s")),
    scmInfo := Some(ScmInfo(url("https://github.com/jCalamari/velocypack4s"), "git@github.com:jCalamari/velocypack4s.git")),
    developers := List(Developer("jCalamari", "Piotr Fras", "piotrek.fras@gmail.com", url("https://github.com/jCalamari"))),
    licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
    publishMavenStyle := true,
    isSnapshot := version.value endsWith "SNAPSHOT",
    publishTo := Some(if (isSnapshot.value) Opts.resolver.sonatypeSnapshots else Opts.resolver.sonatypeStaging),
    credentials += Credentials(
      "Sonatype Nexus Repository Manager",
      "oss.sonatype.org",
      sys.env.getOrElse("SONATYPE_USER", ""),
      sys.env.getOrElse("SONATYPE_PASS", "")
    )
  )

}

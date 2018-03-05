import com.typesafe.sbt.SbtGit.git

import scala.util.matching.Regex

ThisBuild / scalaVersion := "2.12.4"

ThisBuild / organization := "org.scalamari"

ThisBuild / scalacOptions ++= Seq(
  "-deprecation",
  "-explaintypes",
  "-feature",
  "-unchecked",
  "-Xfatal-warnings",
  "-target:jvm-1.8",
  "-encoding", "UTF-8",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen"
)

// Release

ThisBuild / sonatypeProfileName := organization.value
ThisBuild / homepage := Some(url("https://github.com/jCalamari/velocypack4s"))
ThisBuild / scmInfo := Some(ScmInfo(url("https://github.com/jCalamari/velocypack4s"), "git@github.com:jCalamari/velocypack4s.git"))
ThisBuild / developers := List(Developer("jCalamari", "Piotr Fras", "piotrek.fras@gmail.com", url("https://github.com/jCalamari")))
ThisBuild / licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
ThisBuild / publishMavenStyle := true
ThisBuild / publishTo := Some(if (isSnapshot.value) Opts.resolver.sonatypeSnapshots else Opts.resolver.sonatypeStaging)
ThisBuild / isSnapshot := version.value endsWith "SNAPSHOT"

ThisBuild / credentials += Credentials(
  "Sonatype Nexus Repository Manager",
  "oss.sonatype.org",
  sys.env.getOrElse("SONATYPE_USER", ""),
  sys.env.getOrElse("SONATYPE_PASS", "")
)

// Git

val ReleaseTag: Regex = """^v([\d\.]+)$""".r

ThisBuild / git.baseVersion := "1.0.0"

ThisBuild / git.gitTagToVersionNumber := {
  case ReleaseTag(v) => Some(v)
  case _ => None
}

ThisBuild / git.formattedShaVersion := {
  val suffix = git.makeUncommittedSignifierSuffix(git.gitUncommittedChanges.value, git.uncommittedSignifier.value)
  git.gitHeadCommit.value map {
    _.substring(0, 7)
  } map { sha =>
    git.baseVersion.value + "-" + sha + suffix
  }
}

// PGP

ThisBuild / useGpg := false

usePgpKeyHex("38D42FA57C10A00B")

ThisBuild / pgpPublicRing := baseDirectory.value / "project" / ".gnupg" / "pubring.gpg"

ThisBuild / pgpSecretRing := baseDirectory.value / "project" / ".gnupg" / "secring.gpg"

ThisBuild / pgpPassphrase := sys.env.get("PGP_PASS").map(_.toArray)

addCommandAlias("ci-all", ";+clean ;+compile ;+coverage ;+test ;+coverageReport ;+coverageAggregate ;+package")
addCommandAlias("release", ";+publishSigned ;sonatypeReleaseAll")

def VelocyPackModule(name: String): Project = Project(s"velocypack4s-$name", file(s"modules/$name"))

lazy val noPublishSettings = Seq(
  publish := {},
  publishLocal := {},
  publishArtifact := false,
  skip in publish := true
)

val core = VelocyPackModule("core")
  .settings(Dependencies.core)
  .enablePlugins(GitVersioning)

val macros = VelocyPackModule("macros")
  .dependsOn(core)
  .settings(Dependencies.macros)
  .enablePlugins(GitVersioning)

val tut = VelocyPackModule("tut")
  .dependsOn(macros)
  .settings(tutTargetDirectory := file("."))
  .settings(noPublishSettings)
  .enablePlugins(TutPlugin)

val root = Project("velocypack4s", file("."))
  .settings(noPublishSettings)
  .aggregate(core)
  .aggregate(macros)
  .aggregate(tut)
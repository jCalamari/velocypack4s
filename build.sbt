ThisBuild / scalaVersion := "2.12.4"

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

addCommandAlias("ci-all", ";+clean ;+compile ;+coverage ;+test ;+coverageReport ;+coverageAggregate ;+package")
addCommandAlias("release", ";+publishSigned ;sonatypeReleaseAll")

def VelocyPackModule(name: String): Project = Project(s"velocypack4s-$name", file(s"modules/$name"))
  .settings(Shared.settings)

lazy val noPublishSettings = Seq(
  publish := {},
  publishLocal := {},
  publishArtifact := false
)

lazy val publishSettings = Release.settings ++ Git.settings

val core = VelocyPackModule("core")
  .settings(Dependencies.core)
  .settings(publishSettings)
  .enablePlugins(GitVersioning)

val macros = VelocyPackModule("macros")
  .dependsOn(core)
  .settings(Dependencies.macros)
  .settings(publishSettings)
  .enablePlugins(GitVersioning)

val tut = VelocyPackModule("tut")
  .dependsOn(macros)
  .settings(Tut.settings)
  .settings(noPublishSettings)
  .enablePlugins(TutPlugin)

val root = Project("velocypack4s", file("."))
  .settings(noPublishSettings)
  .settings(Gpg.settings)
  .aggregate(core)
  .aggregate(macros)
  .aggregate(tut)
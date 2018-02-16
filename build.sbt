ThisBuild / scalaVersion := "2.12.4"

def VelocyPackModule(name: String): Project = Project(name, file(s"modules/$name"))

val core = VelocyPackModule("core")
  .settings(Dependencies.core)

val macros = VelocyPackModule("macros")
  .settings(Dependencies.macros)
  .dependsOn(core)

// TODO no artifacts
val tut = VelocyPackModule("tut")
  .dependsOn(macros)
  .settings(Tut.settings: _*)
  .enablePlugins(TutPlugin)

val root = Project("velocypack4s", file("."))
  .aggregate(core)
  .aggregate(macros)
  .aggregate(tut)
ThisBuild / scalaVersion := "2.12.4"

def VelocyPackModule(name: String): Project = Project(name, file(s"modules/$name"))

val core = VelocyPackModule("core")
  .settings(Dependencies.core)

val shapeless = VelocyPackModule("shapeless")
  .settings(Dependencies.shapeless)
  .dependsOn(core)

val root = Project("velocypack-module-scala", file("."))
  .aggregate(core)
  .aggregate(shapeless)
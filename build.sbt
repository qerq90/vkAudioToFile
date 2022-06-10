ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

val zioVersion = "1.0.14"
val zhttpVersion = "1.0.0.0-RC27"
val circeVersion = "0.14.1"
val pureconfigVersion = "0.17.1"

lazy val root = (project in file("."))
  .settings(
    name := "vkAudioToFile"
  )

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "io.d11" %% "zhttp" % zhttpVersion,
  "dev.zio" %% "zio" % zioVersion,
  "com.github.pureconfig" %% "pureconfig" % pureconfigVersion,
)

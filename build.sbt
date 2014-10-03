name := """weather-anaytics"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  javaWs,
  "com.google.guava" % "guava" % "18.0",
  "com.google.inject" % "guice" % "3.0",
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4"
)


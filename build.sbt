name := """scala-meetup-november"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "org.reactivemongo" %%  "play2-reactivemongo" % "0.10.5.0.akka23",
  "org.scalatest"     %   "scalatest_2.11"      % "2.2.2"             % "test"
)

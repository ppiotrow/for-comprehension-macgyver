name := """for-comprehension-macgyver"""

scalaVersion := "2.11.7"

scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-encoding", "utf8")

scalariformSettings

libraryDependencies ++=Seq(
  "org.scala-lang" % "scala-reflect" % "2.11.7",
  "org.scalaz" %% "scalaz-core" % "7.1.3",
  "com.typesafe.akka" %% "akka-actor" % "2.3.12")


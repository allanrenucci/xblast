name := "xblast"

version := "1.0"

scalaVersion := "2.11.8"

scalacOptions ++= Seq(
  "-Xlint",
  "-deprecation",
  "-feature",
  "-unchecked"
)

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"

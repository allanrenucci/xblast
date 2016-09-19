name := "xblast"

version := "1.0"

scalaVersion := "2.11.8"

scalacOptions ++= Seq(
  "-Xlint",
  "-deprecation",
  "-feature",
  "-unchecked"
)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"

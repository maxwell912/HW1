import Dependencies._

ThisBuild / scalaVersion     := "2.13.11"
ThisBuild / version          := "0.1.0-SNAPSHOT"

ThisBuild / libraryDependencies += scalaTest

lazy val root = (project in file("."))
  .settings(
    name := "converter",
  )

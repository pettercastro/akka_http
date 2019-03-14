name := "akkahttp"

version := "0.1"

scalaVersion := "2.11.11"

val services = project.in(file("services"))
val app = project.in(file("app")).dependsOn(services)

val root =
  project.in(file("."))
    .aggregate(app)

scalaVersion := "2.11.11"

val akkaVersion = "2.5.19"
val akkaHttpVersion = "10.1.7"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.7",

  "com.github.swagger-akka-http" %% "swagger-akka-http" % "1.0.0",
  "org.zalando" %% "scala-jsonapi" % "0.6.2",

  "org.scalatest" %% "scalatest" % "3.0.5" % Test,
  "com.intuit.karate" % "karate-netty" % "0.9.1" % Test,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test
)
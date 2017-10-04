name := "sns"

version := "0.1.3"

scalaVersion := "2.11.8"

// sbt-assembly
assemblyJarName in assembly := s"sns-${version.value}.jar"
test in assembly := {}

libraryDependencies ++= {
  val akkaVersion = "2.4.9"

  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-experimental" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-xml-experimental" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaVersion
    ,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaVersion % Test,

    "org.slf4j" % "slf4j-api" % "1.7.2",
    "ch.qos.logback" % "logback-classic" % "1.0.7",
    "com.typesafe.akka" %% "akka-camel" % akkaVersion,
    "org.scalatest" %% "scalatest" % "3.0.0" % Test
  )
}

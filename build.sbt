name := "akka-study"

version := "1.5"

scalaVersion := "2.12.4"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

scalacOptions ++= Seq("-deprecation","-feature","-language:postfixOps")

javacOptions ++= Seq("-Xlint:deprecation","-Xlint:unchecked")

val akkaVersion = "2.5.10"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion
    withSources() withJavadoc(), // Classic Actors, Typed Actors, IO Actor etc.
  "com.typesafe.akka" %% "akka-remote" % akkaVersion
    withSources() withJavadoc(), // Remote Actors
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
    withSources() withJavadoc(), // SLF4J Event Handler Listener

  "org.slf4j" % "slf4j-simple" % "1.7.7"
    withSources() withJavadoc(), // SLF4J Simple

  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
    withSources() withJavadoc(), // ScalaTest for Testing
  "com.novocode" % "junit-interface" % "0.10" % "test", // To Use JUnit for Java Testing
  "junit" % "junit" % "4.12" % "test",
  "org.easytesting" % "fest-assert" % "1.4" % "test"
    withSources() withJavadoc(), // Made for better Java Unit Assertions
  "com.typesafe.akka" %% "akka-cluster" % akkaVersion withSources()
    withJavadoc(), // akka-cluster
  "com.typesafe.akka" %% "akka-stream" % akkaVersion
    withSources() withJavadoc(),
  "com.typesafe.akka" %% "akka-testkit" % "2.5.11" % Test
)

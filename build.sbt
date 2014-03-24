name := "Akka Study"

version := "1.0"

scalaVersion := "2.10.3"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

scalacOptions ++= "-deprecation" :: "-feature" :: "-language:postfixOps" :: Nil

javacOptions ++= "-Xlint:deprecation" :: "-Xlint:unchecked" :: Nil

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.0" withSources() withJavadoc(), // Classic Actors, Typed Actors, IO Actor etc.
  "com.typesafe.akka" %% "akka-remote" % "2.3.0" withSources() withJavadoc(), // Remote Actors
  "com.typesafe.akka" %% "akka-slf4j" % "2.3.0" withSources() withJavadoc(), // SLF4J Event Handler Listener
  "org.slf4j" % "slf4j-simple" % "1.7.6" withSources() withJavadoc(), // SLF4J Simple
  "org.scalatest" %% "scalatest" % "2.0" % "test" withSources() withJavadoc(), // ScalaTest for Testing
  "com.novocode" % "junit-interface" % "0.10" % "test", // To Use JUnit for Java Testing
  "org.easytesting" % "fest-assert" % "1.4" % "test" withSources() withJavadoc() // Made for better Java Unit Assertions
)

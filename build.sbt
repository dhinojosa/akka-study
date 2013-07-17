name := "Akka Study"

version := "1.0"

scalaVersion := "2.10.0"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

scalacOptions ++= "-feature" :: "-language:postfixOps" :: Nil

javacOptions += "-Xlint:deprecation"


libraryDependencies ++= Seq (
   "com.typesafe.akka" %% "akka-actor" % "2.1.4" withSources() withJavadoc(),         // Classic Actors, Typed Actors, IO Actor etc.
   "com.typesafe.akka" %% "akka-remote" % "2.1.4" withSources() withJavadoc(),        // Remote Actors
   "com.typesafe.akka" %% "akka-slf4j" % "2.1.4" withSources() withJavadoc(),         // SLF4J Event Handler Listener
   "org.slf4j" % "slf4j-simple" % "1.7.2" withSources() withJavadoc(),                // SLF4J Simple
   "org.scalatest" % "scalatest_2.10" % "1.9.1" % "test" withSources() withJavadoc(), // ScalaTest for Testing
   "com.novocode" % "junit-interface" % "0.10-M3" % "test",                           // To Use JUnit for Java Testing
   "org.easytesting" % "fest-assert" % "1.4" % "test" withSources() withJavadoc()     // Made for better Java Unit Assertions
)


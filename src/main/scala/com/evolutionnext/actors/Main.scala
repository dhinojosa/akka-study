package com.evolutionnext.actors

import akka.actor.{Props, ActorSystem}


object Main extends App {
  val system = ActorSystem("MySystem")
  val myActor = system.actorOf(Props[MyActor], name = "myactor")
}

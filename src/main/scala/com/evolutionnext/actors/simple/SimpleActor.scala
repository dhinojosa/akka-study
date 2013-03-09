package com.evolutionnext.actors.simple

import akka.actor.{ActorSystem, Actor, Props}
import akka.event.Logging

class MyActor extends Actor {
  val log = Logging(context.system, this)
  def receive = {
    case "test" ⇒ log.info("received test")
    case _      ⇒ log.info("received unknown message")
  }
}

object MyActorRunner extends App {
  val system = ActorSystem("MySystem")
  val greeter = system.actorOf(Props[MyActor], name = "greeter")
  greeter ! "test"
  greeter ! "potato fries"
}

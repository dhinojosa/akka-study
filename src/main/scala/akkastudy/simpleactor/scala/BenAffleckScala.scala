package akkastudy.simpleactor.scala

import akka.actor.Actor
import akka.event.Logging

class BenAffleckScala extends Actor {
  val log = Logging(context.system, this)

  def receive = {
    case x: String =>
      val selection = context.actorSelection("../mattDamonScala")
      log.info("Ben: Sending message to Matt Damon")
      selection.tell("Hello, Matt, how many fingers am I holding up?", self)
    case i: Integer =>
      log.info("Ben: Matt Damon gave me {}", i)
    case x =>
      unhandled(x)
  }
}

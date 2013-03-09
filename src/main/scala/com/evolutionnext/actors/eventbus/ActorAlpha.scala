package com.evolutionnext.actors.eventbus

import akka.actor.{Actor, ActorLogging}

class ActorAlpha extends Actor with ActorLogging {

  override def preStart() {
    context.system.eventStream.subscribe(context.self, classOf[ActorBeta])
  }

  def receive = {
    case "One" => log.info("One assigned")
    case "Two" => log.info("Two assigned")
    case _ => log.info("Other assigned")
  }
}

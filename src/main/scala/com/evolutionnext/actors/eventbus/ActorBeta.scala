package com.evolutionnext.actors.eventbus

import akka.actor.{ActorLogging, Actor}

class ActorBeta extends Actor with ActorLogging {


  def receive = {
    case "One" => {
      log.info("Actor Beta : One assigned")
    }
    case "Two" => log.info("Two assigned")
    case _ => log.info("Other assigned")
  }
}

package akkastudy.askactor.scala

import akka.event.Logging
import akka.actor.Actor

class AskActor extends Actor {
  val log = Logging(context.system, this)

  def receive = {
    case "Ping" ⇒ sender ! "Pong"
    case _ ⇒ log.info("received unknown message test in AskActor")
  }
}

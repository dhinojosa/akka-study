package akkastudy.askactor.scala

import akka.event.Logging
import akka.actor.Actor

class AskActor extends Actor {
  val log = Logging(context.system, this)

  def receive = {
    case "Ping" ⇒ {
      log.info("Recieved your ping, coming back at ya!")
      Thread.sleep(3000)
      sender ! "Pong"
    }
    case _ ⇒ log.info("received unknown message test in AskActor")
  }
}

package akkastudy.futures.scala

import akka.actor.Actor
import akka.event.Logging

class FuturesActor extends Actor {

  val log = Logging(context.system, this)

  def receive = {
    case "What is the meaning of love?" ⇒ {
      Thread.sleep(2000)
      sender ! "I am not quite sure"
      sender ! "Wait I know, Beef and Cheddar!"
    }
    case _ ⇒ {
      log.info("received unknown message in FuturesActor")
      sender ! "Complete chaos in your life"
    }
  }
}

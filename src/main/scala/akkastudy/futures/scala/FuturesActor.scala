package akkastudy.futures.scala

import akka.actor.Actor
import akka.event.Logging
import concurrent.Future

class FuturesActor extends Actor {

  import context.dispatcher

  val log = Logging(context.system, this)

  def receive = {
    case "What is the meaning of love?" ⇒ {
      sender ! Future {
        "I am not quite sure"
      }
    }
    case _ ⇒ {
      log.info("received unknown message in FuturesActor")
      sender ! Future {
        "Complete chaos in your life"
      }
    }
  }
}

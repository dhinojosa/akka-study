package akkastudy.supervisorstrategy.scala

import akka.actor.{Actor, Props}

class OneForOneGrandparentActor extends Actor {

  import akka.actor.OneForOneStrategy
  import akka.actor.SupervisorStrategy._
  import scala.concurrent.duration._

  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
      case _: ArithmeticException ⇒ Resume
      case _: NullPointerException ⇒ Restart
      case _: IllegalArgumentException ⇒ Stop
      case _: Exception ⇒ Escalate
    }

  def receive = {
    case p: Props ⇒ sender ! context.actorOf(p)
  }
}

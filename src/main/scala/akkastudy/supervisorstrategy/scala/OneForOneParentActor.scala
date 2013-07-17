package akkastudy.supervisorstrategy.scala

import akka.actor.{Actor, Props}

class OneForOneParentActor extends Actor {

  import akka.actor.OneForOneStrategy
  import akka.actor.SupervisorStrategy._
  import scala.concurrent.duration._

  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
      case _: ArithmeticException ⇒ Escalate
      case _: NullPointerException ⇒ Escalate
      case _: IllegalArgumentException ⇒ Escalate
      case _: Exception ⇒ Restart
    }

  def receive = {
    case p: Props ⇒ sender ! context.actorOf(p)
  }
}

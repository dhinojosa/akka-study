package akkastudy.supervisorstrategy.scala

import akka.actor.{AllForOneStrategy, Actor, Props}

class AllForOneParentActor extends Actor {

  import akka.actor.SupervisorStrategy._
  import scala.concurrent.duration._

  override val supervisorStrategy =
    AllForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 2.minutes) {
      case _: ArithmeticException ⇒ Escalate
      case _: NullPointerException ⇒ Escalate
      case _: IllegalArgumentException ⇒ Escalate
      case _: Exception ⇒ Stop
    }

  def receive = {
    case p: Props ⇒ sender ! context.actorOf(p)
  }
}

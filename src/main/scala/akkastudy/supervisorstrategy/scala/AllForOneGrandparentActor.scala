package akkastudy.supervisorstrategy.scala

import akka.actor.{AllForOneStrategy, Actor, Props}

class AllForOneGrandparentActor extends Actor {

  import akka.actor.SupervisorStrategy._
  import scala.concurrent.duration._

  override val supervisorStrategy =
    AllForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
      case _: ArithmeticException ⇒ Resume
      case _: NullPointerException ⇒ Restart
      case _: IllegalArgumentException ⇒ Stop
      case _: Exception ⇒ Escalate
    }

  def receive = {
    case p: Props ⇒ sender ! context.actorOf(p)
  }
}

package akkastudy.faulttolerance.scala

import akka.actor.{Props, Actor}
import scala.concurrent.duration._

/**
 * ParentActor is an actor used to initialize the supervisor strategy of all
 * its children, in this case a {@link OneToOneStrategy}
 */
class ParentActor extends Actor {

  import akka.actor.OneForOneStrategy
  import akka.actor.SupervisorStrategy._


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

/**
 * Child Actor that responds to three messages. An exception which forwards the same
 * exception, an integer that overrides the state and the string "Get" which will return
 * the state of the Actor
 */
class Child extends Actor {
  var state = 0

  def receive = {
    case ex: Exception ⇒ throw ex
    case x: Int ⇒ state = x
    case "get" ⇒ sender ! state
  }
}

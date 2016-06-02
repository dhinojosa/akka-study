package akkastudy.supervisorstrategy.scala

import akka.actor.Actor
import akka.event.Logging

class ExceptionalChildActor extends Actor {

  val log = Logging(context.system, this)

  def receive = {
    case "ArithmeticException" => throw new ArithmeticException("Oh no")
    case "NullPointerException" => throw new NullPointerException("Oh no")
    case "IllegalArgumentException" => throw new IllegalArgumentException("Oh no")
    case "Exception" => throw new Exception("Oh no")
    case x @ _ => sender ! s"Message Received: $x"
  }

  @throws[Exception]
  override def postStop {
    log.info("Exceptional Child Actor Stopped")
    super.postStop
  }

  @throws[Exception]
  override def postRestart(reason: Throwable) {
    log.info("Exceptional Child Actor Restarted")
    super.postRestart(reason)
  }
}

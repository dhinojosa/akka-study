package akkastudy.supervisorstrategy.scala

import akka.actor.Actor

class ExceptionalChildActor extends Actor {
  def receive = {
    case "ArithmeticException" => throw new ArithmeticException("Oh no")
    case "NullPointerException" => throw new NullPointerException("Oh no")
    case "IllegalArgumentException" => throw new IllegalArgumentException("Oh no")
    case "Exception" => throw new Exception("Oh no")
    case x @ _ => sender ! s"Message Received: $x"
  }
}

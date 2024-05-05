package akkastudy.testkit.scala

import akka.actor.{Actor, ActorLogging}

class HelloActor extends Actor with ActorLogging {
  override def receive: Receive = {
    case "hello" => sender ! "hello world"
    case x => unhandled(x)
  }
}

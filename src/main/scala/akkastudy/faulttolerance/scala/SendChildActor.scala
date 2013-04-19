package akkastudy.faulttolerance.scala

import akka.actor.{Props, ActorRef, Actor}

class SendChildActor(val parent: ActorRef) extends Actor {
  var child: Option[ActorRef] = None

  def receive = {
    case x: ActorRef => child = Some(x)
    case "ArithmeticException" => child.get ! new ArithmeticException("Oh no")
    case "NullPointerException" => child.get ! new NullPointerException("Oh no")
    case "IllegalArgumentException" => child.get ! new IllegalArgumentException("Oh no")
    case "Exception" => child.get ! new Exception("Oh no")
    case x: Int => println("Got " + x)
    case "send" => child.get ! 4
    case "get" => child.get ! "get"
    case "props" => parent ! Props[Child]
  }
}

package akkastudy.faulttolerance.scala

import akka.actor.{ActorRef, Actor}

class SendChildActor(val parent: ActorRef) extends Actor {
  var child: Option[ActorRef] = None

  def receive = {
    case x: ActorRef => child = Some(x)
    case "ArithmeticException" => child.get ! new ArithmeticException("Oh no")
    case "NullPointerException" => child.get ! new NullPointerException("Oh no")
    case "IllegalArgumentException" => child.get ! new IllegalArgumentException("Oh no")
    case "Exception" => child.get ! new Exception("Oh no")
    case x: Int => println(child.get ! x)
    case "get" => println("Calling get %s".format(child.get ! "get"))
  }
}

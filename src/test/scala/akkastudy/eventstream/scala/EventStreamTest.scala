package akkastudy.eventstream.scala

import akka.actor.{DeadLetter, Props, ActorSystem}
import org.scalatest.FlatSpec

class EventStreamTest extends FlatSpec {
  behavior of "An Actor System should"

  it should "throw send a dead letter if the actor is not found" in {
    val system = ActorSystem("MySystem")
    val deadLetterActor = system.actorOf(Props(classOf[DeadLetterListenerActor]), "deadLetterListenerActor")
    system.eventStream.subscribe(deadLetterActor, classOf[DeadLetter])
    val myActor = system.actorSelection("akka://MySystem/user/somethingElseIShouldn\'tBeLookingFor")
    myActor ! "Simple Test"
  }
}
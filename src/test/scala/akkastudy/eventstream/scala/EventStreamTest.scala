package akkastudy.eventstream.scala

import akka.actor.{DeadLetter, Props, ActorSystem}
import org.scalatest.FlatSpec
import akkastudy.eventbus.scala.SimpleActor

class EventStreamTest extends FlatSpec {
  behavior of "An Actor System should"

  it should "throw send a dead letter if the actor is not found" in {
    val system = ActorSystem("MySystem")
    val listenerActor = system.actorOf(Props(classOf[ListenerActor]), "listenerActor")
    system.eventStream.subscribe(listenerActor, classOf[DeadLetter])
    val myActor = system.actorSelection("akka://MySystem/user/somethingElseIShouldn\'tBeLookingFor")
    myActor ! "Simple Test"
  }

  it should "throw send refer to an actor when an event happens" in {
    val system = ActorSystem("MySystem")
    val listenerActor = system.actorOf(Props(classOf[ListenerActor]), "listenerActor")
    val simpleActor = system.actorOf(Props(classOf[SimpleActor]), "simpleActor")
    system.eventStream.subscribe(listenerActor, classOf[String])
    simpleActor ! "Simple Test"
  }
}
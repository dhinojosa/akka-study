package akkastudy.simpleactor.scala

import org.scalatest.FlatSpec
import akka.actor.{Props, ActorSystem}

class SimpleActorTest extends FlatSpec {
  behavior of "A simple actor"

  it should "receive our message in Java" in {
    val system = ActorSystem("MySystem")
    val myActor = system.actorOf(Props[SimpleActorScala], name = "simpleActorJava")
    myActor ! "Simple Test"
  }
}
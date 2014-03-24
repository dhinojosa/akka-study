package akkastudy.stopactor.scala

import akka.actor.{Props, ActorSystem}
import org.scalatest.FlatSpec

class StopActorTest extends FlatSpec {
  behavior of "stopping an actor"

  it should "stop an actor and run postStop() for cleanup" in {
    val system = ActorSystem("MySystem")
    val myActor0 = system.actorOf(Props[StopActorScala], name = "stopActorScala0")
    val myActor1 = system.actorOf(Props[StopActorScala], name = "stopActorScala1")
    val myActor2 = system.actorOf(Props[StopActorScala], name = "stopActorScala2")
    myActor0 ! "Simple Test"
    myActor1 ! "test"
    system.stop(myActor2)
    Thread.sleep(5000)
  }

  it should "it should stop all actors when the system is shutdown" in {
    val system = ActorSystem("MySystem")
    val myActor0 = system.actorOf(Props[StopActorScala], name = "stopActorScala0")
    val myActor1 = system.actorOf(Props[StopActorScala], name = "stopActorScala1")
    system.actorOf(Props[StopActorScala], name = "stopActorScala2")
    system.actorOf(Props[StopActorScala], name = "stopActorScala3")
    myActor0 ! "Simple Test"
    myActor1 ! "test"
    system.shutdown()
    Thread.sleep(5000)
  }
}

package akkastudy.stopactor.scala

import java.util.concurrent.TimeUnit

import akka.actor.{Props, ActorSystem}
import org.scalatest.FlatSpec

import scala.concurrent.Await
import scala.concurrent.duration.Duration

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
    Await.result(system.terminate(), Duration(10, TimeUnit.SECONDS))
  }

  it should "it should stop all actors when the system is shutdown" in {
    val system = ActorSystem("MySystem")
    val myActor0 = system.actorOf(Props[StopActorScala], name = "stopActorScala0")
    val myActor1 = system.actorOf(Props[StopActorScala], name = "stopActorScala1")
    system.actorOf(Props[StopActorScala], name = "stopActorScala2")
    system.actorOf(Props[StopActorScala], name = "stopActorScala3")
    myActor0 ! "Simple Test"
    myActor1 ! "test"
    Thread.sleep(5000)
    Await.result(system.terminate(), Duration(10, TimeUnit.SECONDS))
  }
}

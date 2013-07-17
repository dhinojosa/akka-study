package akkastudy.killactor.scala

import akka.actor.{Kill, Props, ActorSystem}
import org.scalatest.FlatSpec

class KillActorTest extends FlatSpec {
  behavior of "giving a poison pill to an actor"

  it should "will allow all the messages to process before shutting down" in {

    val system = ActorSystem("MySystem")

    val caesarRef = system.actorOf(Props[CaesarActor], name = "caesarActorScala")
    val brutusRef = system.actorOf(Props[BrutusActor], name = "brutusActorScala")

    brutusRef ! (() => {Thread.sleep(2000)})

    caesarRef ! (() => {Thread.sleep(3000); 1})
    caesarRef ! (() => {Thread.sleep(3000); 2})
    caesarRef ! (() => {Thread.sleep(3000); 3})
    caesarRef ! (() => {Thread.sleep(3000); 4})
    caesarRef ! (() => {Thread.sleep(3000); 5})
    caesarRef ! (() => {Thread.sleep(3000); 6})
    caesarRef ! (() => {Thread.sleep(3000); 7})
    caesarRef ! (() => {Thread.sleep(3000); 8})
    caesarRef ! (() => {Thread.sleep(3000); 9})
    caesarRef ! (() => {Thread.sleep(3000); 10})
    caesarRef ! (() => {Thread.sleep(3000); 11})
    caesarRef ! (() => {Thread.sleep(3000); 12})
  }
}

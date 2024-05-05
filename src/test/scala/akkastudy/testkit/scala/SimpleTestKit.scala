package akkastudy.testkit.scala

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest._

class SimpleTestKit extends TestKit(ActorSystem("MySpec"))
  with ImplicitSender
  with FunSuiteLike
  with BeforeAndAfterAll
  with Matchers {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  test("An actor should send back a hello world") {
      val helloActor = system.actorOf(Props[HelloActor], name = "helloActor")
      helloActor ! "hello"
      expectMsg("hello world")
  }
}

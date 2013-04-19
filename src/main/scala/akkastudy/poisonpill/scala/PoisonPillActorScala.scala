package akkastudy.poisonpill.scala

import akka.actor.Actor
import akka.event.Logging

class PoisonPillActorScala extends Actor {
  val log = Logging(context.system, this)

  def receive = {
    case "test" ⇒ log.info("received message test in Simple Actor Scala")
    case _ ⇒ log.info("received unknown message test in Simple Actor Scala")
  }

  override def postStop() {
    println("Actor stopped")
  }
}
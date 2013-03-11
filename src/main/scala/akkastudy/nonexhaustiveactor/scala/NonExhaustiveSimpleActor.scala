package akkastudy.nonexhaustiveactor.scala

import akka.actor.{Props, ActorSystem, ActorLogging, Actor}

class NonExhaustiveSimpleActor extends Actor with ActorLogging {
  override def preStart() {
    log.info("Running Pre-start")
  }

  def receive = {
    case "One" => log.info("received One")
    case "Two" => log.info("received Two")
    case "Three" => log.info("received Three")
  }
}

object NonExhaustiveSimpleActor extends App {
  val system = ActorSystem("MySystem")
  val actor = system.actorOf(Props[NonExhaustiveSimpleActor], name = "nonExhaustiveSimpleActor")
  actor ! "One"
  actor ! "Two"
  actor ! "Three"
  actor ! "Four"
}

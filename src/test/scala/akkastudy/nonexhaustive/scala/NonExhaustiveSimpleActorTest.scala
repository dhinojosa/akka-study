package akkastudy.nonexhaustive.scala

import org.scalatest.{Matchers, FlatSpec}
import akka.actor.{Props, ActorSystem}
import akkastudy.nonexhaustiveactor.scala.NonExhaustiveSimpleActor

class NonExhaustiveSimpleActorTest extends FlatSpec with Matchers {
  behavior of "An Actor"

  it should "be exhastive and not leave anything to chance" in {
    val system = ActorSystem("MySystem")
    val actor = system.actorOf(Props[NonExhaustiveSimpleActor], name = "nonExhaustiveSimpleActor")
    actor ! "One"
    actor ! "Two"
    actor ! "Three"
    actor ! "Four"
  }
}
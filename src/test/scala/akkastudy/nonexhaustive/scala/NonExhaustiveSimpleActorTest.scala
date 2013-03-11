package akkastudy.nonexhaustive.scala

import org.scalatest.FlatSpec
import org.scalatest.matchers.MustMatchers
import akka.actor.{Props, ActorSystem}
import akkastudy.nonexhaustiveactor.scala.NonExhaustiveSimpleActor

class NonExhaustiveSimpleActorTest extends FlatSpec with MustMatchers {
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
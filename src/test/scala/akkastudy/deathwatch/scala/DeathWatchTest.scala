package akkastudy.deathwatch.scala

import org.scalatest.{Matchers, FlatSpec}
import akka.actor.{Props, ActorSystem}

/**
 *
 * @author Daniel Hinojosa
 * @since 3/26/14 12:09 PM
 *        url: <a href="http://www.evolutionnext.com">http://www.evolutionnext.com</a>
 *        email: <a href="mailto:dhinojosa@evolutionnext.com">dhinojosa@evolutionnext.com</a>
 *        tel: 505.363.5832
 */
class DeathWatchTest extends FlatSpec with Matchers {
  behavior of "Death Watch"

  it should "An actor can be notified of a termination by using the same supervisory facilities" in {
    val actorSystem = ActorSystem("DeathWatchActorSystem")
    val deathWatcherActor = actorSystem.actorOf(Props[DeathWatcherActor], "DeathWatchActor")
    deathWatcherActor ! "kill"
  }
}

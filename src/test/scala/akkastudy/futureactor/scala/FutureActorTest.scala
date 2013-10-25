package akkastudy.futureactor.scala

import org.scalatest.FlatSpec
import org.scalatest.matchers.MustMatchers
import akka.actor
import actor.{Props, ActorSystem}
import akka.util.Timeout
import scala.concurrent.duration._

/**
 *
 * @author Daniel Hinojosa
 * @since 10/23/13 10:16 PM
 *        url: <a href="http://www.evolutionnext.com">http://www.evolutionnext.com</a>
 *        email: <a href="mailto:dhinojosa@evolutionnext.com">dhinojosa@evolutionnext.com</a>
 *        tel: 505.363.5832
 */
class FutureActorTest extends FlatSpec with MustMatchers {
  behavior of "An actor that is waiting for a Future[T]"

  it should "not process the future if the thread that runs the actor is reclaimed" in {
    implicit val timeout = Timeout(3 seconds)
    val system = ActorSystem("MySystem")
    val futureActor = system.actorOf(Props[FuturesActor], "futuresActor")
    futureActor ! "Compute"
    println("Done")
  }
}

package akkastudy.locationtransparency

import akka.actor.{Actor, ActorLogging}
import akka.actor.Actor.Receive

/**
 *
 * @author Daniel Hinojosa
 * @since 4/17/14 3:16 PM
 *        url: <a href="http://www.evolutionnext.com">http://www.evolutionnext.com</a>
 *        email: <a href="mailto:dhinojosa@evolutionnext.com">dhinojosa@evolutionnext.com</a>
 *        tel: 505.363.5832
 */
class ChildActor extends Actor with ActorLogging {
  def receive = {
    case _ @ x ⇒
      log.info("received message" + x + " in Child Actor Scala")
  }
}

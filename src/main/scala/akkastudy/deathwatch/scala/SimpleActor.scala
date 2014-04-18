package akkastudy.deathwatch.scala

import akka.actor.{Actor, ActorLogging}

/**
 *
 * @author Daniel Hinojosa
 * @since 4/11/14 10:12 AM
 *        url: <a href="http://www.evolutionnext.com">http://www.evolutionnext.com</a>
 *        email: <a href="mailto:dhinojosa@evolutionnext.com">dhinojosa@evolutionnext.com</a>
 *        tel: 505.363.5832
 */
class SimpleActor extends Actor with ActorLogging {
  override def receive = {
    case _ => log.info("Just doing what ever you tell me")
  }
}

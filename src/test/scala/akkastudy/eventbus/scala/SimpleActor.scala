package akkastudy.eventbus.scala

import akka.actor.{ActorLogging, Actor}

/**
 *
 * @author Daniel Hinojosa
 * @since 4/9/14 9:56 PM
 *        url: <a href="http://www.evolutionnext.com">http://www.evolutionnext.com</a>
 *        email: <a href="mailto:dhinojosa@evolutionnext.com">dhinojosa@evolutionnext.com</a>
 *        tel: 505.363.5832
 */
class SimpleActor extends Actor with ActorLogging {
  override def receive: Receive = {
    case "Alpha" => log.info("Alpha Received")
    case "Beta" => log.info("Beta Received")
    case "Gamma" => log.info("Gamma Received")
    case _ => log.info("Other Received")
  }
}

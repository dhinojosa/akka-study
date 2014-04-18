package akkastudy.killactor.scala

import akka.event.Logging
import akka.actor.Actor

/**
 *
 * @author Daniel Hinojosa
 * @since 6/28/13 2:22 PM
 *        url: <a href="http://www.evolutionnext.com">http://www.evolutionnext.com</a>
 *        email: <a href="mailto:dhinojosa@evolutionnext.com">dhinojosa@evolutionnext.com</a>
 *        tel: 505.363.5832
 */
class CaesarActor extends Actor {

  val log = Logging(context.system, this)

  def receive = {
    case x: (() => Any) =>
      log.debug("Message accepted processing function, and returning {}", x())
    case _ => log.debug("Unknown message received")
  }
}

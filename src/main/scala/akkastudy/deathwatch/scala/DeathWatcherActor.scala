package akkastudy.deathwatch.scala

import akka.actor._

/**
 *
 * @author Daniel Hinojosa
 * @since 4/11/14 10:12 AM
 *        url: <a href="http://www.evolutionnext.com">http://www.evolutionnext.com</a>
 *        email: <a href="mailto:dhinojosa@evolutionnext.com">dhinojosa@evolutionnext.com</a>
 *        tel: 505.363.5832
 */
class DeathWatcherActor extends Actor with ActorLogging {

  var simpleActor: ActorRef = _

  override def aroundPreStart() {
    log.info("Beginning Pre Start")
    preStart()
    log.info("Ending Pre Start")
  }

  override def preStart() {
    simpleActor = context.actorOf(Props[SimpleActor], "SimpleActor")
    context.watch(simpleActor)
  }

  override def receive = {
    case "kill" => context.stop(simpleActor)
    case Terminated(actorRef) =>
      log.info("Simple Actor has been terminated")
  }
}

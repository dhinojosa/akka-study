package akkastudy.remote.scala

import akka.actor.{ActorRef, Actor, ActorLogging}
import akka.actor.Actor.Receive

/**
 *
 * @author Daniel Hinojosa
 * @since 4/15/14 12:13 PM
 *        url: <a href="http://www.evolutionnext.com">http://www.evolutionnext.com</a>
 *        email: <a href="mailto:dhinojosa@evolutionnext.com">dhinojosa@evolutionnext.com</a>
 *        tel: 505.363.5832
 */
class LocalActorScala extends Actor with ActorLogging {

  override def receive: Receive = {
    case (actorRef:ActorRef, msg:String) => actorRef ! msg
    case _ @ x => log.info(s"Recieved message $x")
  }
}

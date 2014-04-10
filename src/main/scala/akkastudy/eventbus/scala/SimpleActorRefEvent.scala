package akkastudy.eventbus.scala

import akka.actor.ActorRef

/**
 *
 * An Event that wraps an ActorRef as the addressee of where this event is going to.
 * 
 * @author Daniel Hinojosa
 * @since 3/28/14 4:12 PM
 *        url: <a href="http://www.evolutionnext.com">http://www.evolutionnext.com</a>
 *        email: <a href="mailto:dhinojosa@evolutionnext.com">dhinojosa@evolutionnext.com</a>
 *        tel: 505.363.5832
 */

case class SimpleActorRefEvent(category: String, message:String, time:String, to: ActorRef)

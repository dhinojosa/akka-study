package akkastudy.locationtransparency

import akka.actor.{ActorIdentity, Identify, ActorLogging, Actor}

/**
 *
 * @author Daniel Hinojosa
 * @since 4/17/14 3:16 PM
 *        url: <a href="http://www.evolutionnext.com">http://www.evolutionnext.com</a>
 *        email: <a href="mailto:dhinojosa@evolutionnext.com">dhinojosa@evolutionnext.com</a>
 *        tel: 505.363.5832
 */
class ParentActor extends Actor with ActorLogging {
  val id = 201123

  override def preStart() {
    val actorSelection =
      context.actorSelection("akka.tcp://RemoteActorSystem@127.0.0.1:10190/user/childActor")
    actorSelection ! Identify(id)
    actorSelection ! "Cool Try this"
  }

  override def receive = {
    case ActorIdentity(`id`, Some(actorRef)) =>
      log.debug("Received ActorIdentity({}, {})", id, Some(actorRef))
      context.watch(actorRef)
      log.debug("List of children: {}", context.children)
    case ("child", msg) => log.info("Going to send info")
    case _ @ x=> log.info("Received information: {}", x)
  }
}

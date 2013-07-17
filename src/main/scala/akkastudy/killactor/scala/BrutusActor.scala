package akkastudy.killactor.scala

import akka.actor.{Kill, Actor}
import akka.event.Logging

/**
  *
  * @author Daniel Hinojosa
  * @since 6/28/13 2:22 PM
  *        url: <a href="http://www.evolutionnext.com">http://www.evolutionnext.com</a>
  *        email: <a href="mailto:dhinojosa@evolutionnext.com">dhinojosa@evolutionnext.com</a>
  *        tel: 505.363.5832
  */
class BrutusActor extends Actor {
  val log = Logging(context.system, this)

  def receive = {
    case x: Function0[_] => {
      x()
      log.debug("I am going to kill Julius Caesar!")
      val caesarRef = context.system.actorFor("akka://MySystem/user/caesarActorScala")
      caesarRef ! Kill
    }
    case _ => log.debug("Brutus shall bite thine tongue")
  }
}
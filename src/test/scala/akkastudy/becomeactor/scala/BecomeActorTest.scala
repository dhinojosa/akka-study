package akkastudy.becomeactor.scala

import org.scalatest.FlatSpec
import org.scalatest.matchers.MustMatchers
import akka.actor.{ActorSystem, Props}
import akkastudy.SenderActor

class BecomeActorTest extends FlatSpec with MustMatchers {
  behavior of "An Actor System should"

  it should "An actor can rotate state with become and unbecome" in {
    val system = ActorSystem("MySystem")
    val becomeActor = system.actorOf(Props[BecomeActor], name = "becomeActor")
    implicit val senderActor =
      system.actorOf(Props[SenderActor], name = "senderActor") //the implicit actor that is sent
    (becomeActor!("goHiking"))
    (becomeActor!("goHiking"))
    (becomeActor!("useFoulLanguage"))
    (becomeActor!("goHiking"))              //No
    (becomeActor!("getDrunkAndCrashCar"))   //No
    (becomeActor!("goHiking"))              //No
    (becomeActor!("askForgiveness"))        //No
    (becomeActor!("spendTimeInPrison"))     //No
    (becomeActor!("goOutToDinner"))         //Nom
    (becomeActor!("askForgiveness"))        //No Response
    (becomeActor!("goHiking"))              //Good Hike!
  }
}
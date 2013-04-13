package akkastudy.poisonpill.scala

import akka.actor.{PoisonPill, Props, ActorSystem}
import org.scalatest.FlatSpec

class PoisonPillActorTest extends FlatSpec {
   behavior of "giving a poison pill to an actor"

   it should "will allow all the messages to process before shutting down" in {
     val system = ActorSystem("MySystem")
     val myActor0 = system.actorOf(Props[PoisonPillActorScala], name = "poisonPillActorScala0")
     system.actorOf(Props[PoisonPillActorScala], name = "poisonPillActorScala3")
     Thread.sleep(5000)
     myActor0 ! "Test"
     myActor0 ! "Whoa"
     myActor0 ! 3
     myActor0 ! PoisonPill.getInstance
     myActor0 ! 3
     Thread.sleep(5000)
   }
 }

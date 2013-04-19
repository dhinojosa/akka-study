package akkastudy.faulttolerance.scala

import org.scalatest.FlatSpec
import org.scalatest.matchers.MustMatchers
import akka.actor.{ActorSystem, Props}
import akkastudy.SenderActor
import akkastudy.becomeactor.scala.BecomeActor

class FaultToleranceTest extends FlatSpec with MustMatchers {
   behavior of "A child actor "

//   it should "Fault Tolerant System" in {
//     val system = ActorSystem("MySystem")
//     val parent = system.actorOf(Props[ParentActor], name = "dad")
//     val sendChildActor = system.actorOf(Props(new SendChildActor(parent)), name = "sender")
//     sendChildActor ! "props"
//     Thread.sleep(2000)
//     sendChildActor ! "ArithmeticException"
//     Thread.sleep(2000)
//     sendChildActor ! "send"
//     sendChildActor ! "get"
//     Thread.sleep(10000)
//   }

//   it should "Restart with a Null Pointer Exception" in {
//     val system = ActorSystem("MySystem")
//     val parent = system.actorOf(Props[ParentActor], name = "dad")
//     val sendChildActor = system.actorOf(Props(new SendChildActor(parent)), name = "sender")
//     sendChildActor ! "props"
//     Thread.sleep(2000)
//     sendChildActor ! "NullPointerException"
//     Thread.sleep(2000)
//     sendChildActor ! "send"
//     sendChildActor ! "get"
//     Thread.sleep(10000)
//   }
//
//   it should "Escalate with an Exception" in {
//      val system = ActorSystem("MySystem")
//      val parent = system.actorOf(Props[ParentActor], name = "dad")
//      val sendChildActor = system.actorOf(Props(new SendChildActor(parent)), name = "sender")
//      sendChildActor ! "props"
//      Thread.sleep(2000)
//      sendChildActor ! "Exception"
//      Thread.sleep(2000)
//      sendChildActor ! "send"
//      sendChildActor ! "get"
//      Thread.sleep(10000)
//    }
//
    it should "Stop with an IllegalArgumentException" in {
        val system = ActorSystem("MySystem")
        val parent = system.actorOf(Props[ParentActor], name = "dad")
        val sendChildActor = system.actorOf(Props(new SendChildActor(parent)), name = "sender")
        sendChildActor ! "props"
        Thread.sleep(2000)
        sendChildActor ! "IllegalArgumentException"
        Thread.sleep(2000)
        sendChildActor ! "send"
        sendChildActor ! "get"
        Thread.sleep(10000)
    }
 }
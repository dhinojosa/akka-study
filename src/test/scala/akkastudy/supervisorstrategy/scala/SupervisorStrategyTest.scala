package akkastudy.supervisorstrategy.scala

import org.scalatest.{FunSuite, Matchers}
import akka.actor.{ActorRef, Props, ActorSystem}
import akka.util.Timeout
import akka.pattern.ask
import scala.concurrent.duration._


class SupervisorStrategyTest extends FunSuite with Matchers {

  test("Create any child actor and throw an Illegal Argument Exception") {
    val system = ActorSystem("MySystem")

    import system.dispatcher
    implicit val timeout = new Timeout(5 seconds)

    val grandparent = system.actorOf(Props[OneForOneGrandparentActor], name = "GrandparentActorScala")

    val childActorFuture1 = grandparent ? Props[ExceptionalChildActor]

    grandparent ! Props[ExceptionalChildActor]
    grandparent ! Props[ExceptionalChildActor]

    Thread.sleep(3000)
    childActorFuture1 foreach {
      _.asInstanceOf[ActorRef] ! "IllegalArgumentException" //Asynchronous wait for the ref, throw the IAE
    }

    Thread.sleep(5000)
    system.shutdown()
    system.awaitTermination()
  }

  test(
    "Create any child actor and throw a NullPointerException then sends another message since the actor, has been restarted") {

    val system = ActorSystem("MySystem")

    import system.dispatcher
    implicit val timeout = new Timeout(5 seconds)

    val grandparent = system.actorOf(Props[OneForOneGrandparentActor], name = "GrandparentActorScala")

    val childActorFuture1 = grandparent ? Props[ExceptionalChildActor]

    grandparent ! Props[ExceptionalChildActor]
    grandparent ! Props[ExceptionalChildActor]

    Thread.sleep(3000)
    childActorFuture1 foreach {
      a =>
        val actorRef = a.asInstanceOf[ActorRef]
        actorRef ! "NullPointerException" //Asynchronous wait for the ref, throw the IAE
        (actorRef ? "OK").foreach(_ should be("Message Received: OK"))
    }

    Thread.sleep(5000)
    system.shutdown()
    system.awaitTermination()
  }

  test("Resume the child actor if an arithmetic exception is thrown as specified in the strategy at the parent") {

    val system = ActorSystem("MySystem")

    import system.dispatcher
    implicit val timeout = new Timeout(5 seconds)

    val grandparentRef = system.actorOf(Props[OneForOneGrandparentActor], name = "OneForOneGrandparentActorScala")

    grandparentRef ! Props[ExceptionalChildActor]
    grandparentRef ! Props[ExceptionalChildActor]
    grandparentRef ! Props[ExceptionalChildActor]

    Thread.sleep(3000)

    grandparentRef ? Props[ExceptionalChildActor] foreach {
      c =>
        val childActorRef = c.asInstanceOf[ActorRef]
        childActorRef ! "ArithmeticException"
    }

    Thread.sleep(5000)
    system.shutdown()
    system.awaitTermination()
  }

  test("throw restart the child actor, not because of the parent but because of the grandparent") {
    val system = ActorSystem("MySystem")

    import system.dispatcher
    implicit val timeout = new Timeout(5 seconds)

    val grandparent = system.actorOf(Props[OneForOneGrandparentActor], name = "GrandparentActorScala")

    grandparent ! Props[ExceptionalChildActor]
    grandparent ! Props[ExceptionalChildActor]
    grandparent ! Props[ExceptionalChildActor]

    val parentActorFuture = grandparent ? Props[OneForOneParentActor]

    Thread.sleep(3000)
    parentActorFuture foreach {
      a =>
        val parentActorFutureRef = a.asInstanceOf[ActorRef]
        val childActorFutureRef = parentActorFutureRef ? Props[ExceptionalChildActor]
        childActorFutureRef foreach {
          c =>
            val childActorRef = c.asInstanceOf[ActorRef]
            childActorRef ! "NullPointerException"
            childActorRef ? "OK" foreach (o => o should be("Message Received: OK"))
        }
    }

    Thread.sleep(5000)
    system.shutdown()
    system.awaitTermination()
  }

  test("Using a One for One Grandparent Actor, and an All for One Parent, all the parent's will share the same fate") {
    val system = ActorSystem("MySystem")

    import system.dispatcher
    implicit val timeout = new Timeout(5 seconds)

    val grandparent = system.actorOf(Props[OneForOneGrandparentActor], name = "GrandparentActorScala")

    grandparent ! Props[ExceptionalChildActor]
    grandparent ! Props[ExceptionalChildActor]
    grandparent ! Props[ExceptionalChildActor]

    val parentActorFuture = grandparent ? Props[AllForOneParentActor]

    Thread.sleep(3000)
    parentActorFuture foreach {
      a =>
        val parentActorFutureRef = a.asInstanceOf[ActorRef]
        val childActorFutureRef = parentActorFutureRef ? Props[ExceptionalChildActor]
        childActorFutureRef foreach {
          c =>
            val childActorRef = c.asInstanceOf[ActorRef]
            childActorRef ! "NullPointerException"
            childActorRef ? "OK" foreach (o => o should be("Message Received: OK"))
        }
    }
    Thread.sleep(5000)
    system.shutdown()
    system.awaitTermination()
  }
}
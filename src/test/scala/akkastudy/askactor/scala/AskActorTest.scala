package akkastudy.askactor.scala

import java.util.concurrent.TimeUnit

import org.scalatest.{FunSuite, FlatSpec}
import akka.actor.{Props, ActorSystem}
import akka.util.Timeout
import scala.concurrent.Await
import scala.concurrent.duration._

class AskActorTest extends FunSuite {
  test("Call in using an ask"){
    import akka.pattern.ask
    import system.dispatcher

    implicit val timeout = Timeout(5 seconds)
    val system = ActorSystem("MySystem")
    val askActor = system.actorOf(Props[AskActor], "askActor")
    val result = askActor.ask("Ping").mapTo[String]
    result foreach println
    System.out.println("Hooray for me!")
    Thread.sleep(4000)
    Await.result(system.terminate(), Duration(10, TimeUnit.SECONDS))
  }

  test("Call in using an ask with ? method") {
    import akka.pattern.ask
    import system.dispatcher

    implicit val timeout = Timeout(5 seconds)
    val system = ActorSystem("MySystem")
    val askActor = system.actorOf(Props[AskActor], "askActor")
    val result = askActor ? "Ping"
    result foreach println
    Await.result(system.terminate(), Duration(10, TimeUnit.SECONDS))
  }
}
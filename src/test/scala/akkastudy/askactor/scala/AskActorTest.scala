package akkastudy.askactor.scala

import org.scalatest.FlatSpec
import akka.actor.{Props, ActorSystem}
import akka.util.Timeout
import scala.concurrent.duration._

class AskActorTest extends FlatSpec {
  behavior of "An Actor System should"

  it should "Call in using an ask" in {
    import akka.pattern.ask
    import system.dispatcher // Execution Context to support the Future
    implicit val timeout = Timeout(5 seconds)
    val system = ActorSystem("MySystem")
    val askActor = system.actorOf(Props[AskActor], "askActor")
    val openMic = askActor.ask("Ping").mapTo[String]
    openMic foreach println
  }
}
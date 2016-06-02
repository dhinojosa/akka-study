package akkastudy.router.scala

import java.util.concurrent.TimeUnit

import org.scalatest.{Matchers, FlatSpec}
import akka.actor.{Props, ActorSystem}
import akka.routing.FromConfig
import akkastudy.SenderActor
import com.typesafe.config.ConfigFactory
import akkastudy.simpleactor.scala.SimpleActorScala

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class RouterTest extends FlatSpec with Matchers {
  behavior of "a Router"

  it should
    """hand off work to each router to distribute load. It
      | should also do so using configuration""" in {
    val config = ConfigFactory.load() //default configuration
    val system = ActorSystem("MySystem",
        config.getConfig("routing-system").withFallback(config))
    val router = system.
      actorOf(Props[SimpleActorScala].withRouter(FromConfig()), "simplerouter")
    val senderActor = system.actorOf(Props[SenderActor])

    (router ! "test")(senderActor)
    (router ! "test")(senderActor)
    (router ! "test")(senderActor)
    (router ! "test")(senderActor)
    (router ! "test")(senderActor)
    (router ! "test")(senderActor)
    (router ! "test")(senderActor)
    (router ! "test")(senderActor)
    (router ! "test")(senderActor)

    Thread.sleep(15000)
    Await.result(system.terminate(), Duration(10, TimeUnit.SECONDS))
    Thread.sleep(2000)
  }
}

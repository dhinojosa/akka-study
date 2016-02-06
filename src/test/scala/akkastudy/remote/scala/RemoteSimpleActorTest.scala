package akkastudy.remote.scala

import java.util.concurrent.TimeUnit

import org.scalatest.{FunSuite, FlatSpec}
import akka.actor.{Deploy, Props, ActorSystem}
import com.typesafe.config.ConfigFactory

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class RemoteSimpleActorTest extends FunSuite {

  test("receive our message in Scala but remotely") {
    val config = ConfigFactory.load()
    val actorSystemBeta = ActorSystem("actorSystemBeta",
      config.getConfig("remote-system-beta").withFallback(config))
    val actorSystemAlpha  = ActorSystem("actorSystemAlpha",
      config.getConfig("remote-system-alpha").withFallback(config))

    val simpleActor = actorSystemAlpha.actorOf(Props[SimpleActorScala], name = "simpleActorScala")

    simpleActor ! "Do it"

    Thread.sleep(10000)

    Await.result(actorSystemAlpha.terminate(), Duration(10, TimeUnit.SECONDS))
    Await.result(actorSystemBeta.terminate(), Duration(10, TimeUnit.SECONDS))
  }

  test("receive our message in Scala but remotely with different setup") {
    val config = ConfigFactory.load()
    val actorSystemBeta = ActorSystem("actorSystemBeta", config.getConfig("remote-system-beta").withFallback(config))
    val actorSystemAlpha  = ActorSystem("actorSystemAlpha", config.getConfig("remote-system-alpha").withFallback(config))

    val senderActor = actorSystemAlpha.actorOf(Props[SenderActorScala], name="senderActorScala")

    senderActor ! "Do it"

    Thread.sleep(5000)
    Await.result(actorSystemAlpha.terminate(), Duration(10, TimeUnit.SECONDS))
    Await.result(actorSystemBeta.terminate(), Duration(10, TimeUnit.SECONDS))
  }
}
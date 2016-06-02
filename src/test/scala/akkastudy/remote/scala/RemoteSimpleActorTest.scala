package akkastudy.remote.scala

import java.util.concurrent.TimeUnit

import org.scalatest.{FlatSpec, FunSuite}
import akka.actor.{ActorRef, ActorSystem, Deploy, Props}
import akkastudy.remote.java.SimpleActorJava
import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class RemoteSimpleActorTest extends FunSuite {

  test("Test simple message onto a remote actor") {
    val config: Config = ConfigFactory.load

    val remoteSystem = ActorSystem("remoteSystem", config.getConfig("remote-system").withFallback(config))
    val simpleActorScala = remoteSystem.actorOf(Props.create(classOf[SimpleActorScala]), "simpleActorScala")
    simpleActorScala.tell("Send Message", remoteSystem.deadLetters)

    Thread.sleep(10000)
    Await.result(remoteSystem.terminate, Duration.apply(10, TimeUnit.SECONDS))
  }

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
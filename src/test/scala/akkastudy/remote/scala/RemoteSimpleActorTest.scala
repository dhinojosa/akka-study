package akkastudy.remote.scala

import org.scalatest.FlatSpec
import akka.actor.{Deploy, Props, ActorSystem}
import com.typesafe.config.ConfigFactory

class RemoteSimpleActorTest extends FlatSpec {
  behavior of "A simple actor"

  it should "receive our message in Scala but remotely" in {
    val config = ConfigFactory.load()
    val actorSystemBeta = ActorSystem("actorSystemBeta", config.getConfig("remote-system-beta").withFallback(config))
    val actorSystemAlpha  = ActorSystem("actorSystemAlpha", config.getConfig("remote-system-alpha").withFallback(config))

    val simpleActor = actorSystemAlpha.actorOf(Props[SimpleActorScala], name = "simpleActorScala")

    simpleActor ! "Do it"

    Thread.sleep(5000)

    actorSystemAlpha.shutdown()
    actorSystemAlpha.awaitTermination()
    actorSystemBeta.shutdown()
    actorSystemBeta.awaitTermination()
  }

  it should "receive our message in Scala but remotely with different setup" in {
    val config = ConfigFactory.load()
    val actorSystemBeta = ActorSystem("actorSystemBeta", config.getConfig("remote-system-beta").withFallback(config))
    val actorSystemAlpha  = ActorSystem("actorSystemAlpha", config.getConfig("remote-system-alpha").withFallback(config))

    val senderActor = actorSystemAlpha.actorOf(Props[SenderActorScala], name="senderActorScala")

    senderActor ! "Do it"

    Thread.sleep(5000)
    actorSystemAlpha.shutdown()
    actorSystemAlpha.awaitTermination()
    actorSystemBeta.shutdown()
    actorSystemBeta.awaitTermination()
  }

  it should "be able to call a remote actor via URL" in {
    val config = ConfigFactory.load()
    val remoteActorSystem  = ActorSystem("remoteActorSystem", config.getConfig("remote-system").withFallback(config))

    val senderActor = remoteActorSystem.actorOf(Props[SimpleActorScala], name="simpleActorScala")

    senderActor ! "Do it"

    remoteActorSystem.actorSelection("akka.tcp://remoteActorSystem:10190/user/simpleActorScala")

    Thread.sleep(5000)
    remoteActorSystem.shutdown()
    remoteActorSystem.awaitTermination()
  }
}
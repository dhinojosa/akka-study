package akkastudy.remote.scala

import org.scalatest.FlatSpec
import akka.actor.{Props, ActorSystem}
import com.typesafe.config.ConfigFactory

class RemoteSimpleActorTest extends FlatSpec {
  behavior of "A simple actor"

  it should "receive our message in Scala but remotely" in {
    val config = ConfigFactory.load()
    ActorSystem("RemoteActorSystem", config.getConfig("remote-system").withFallback(config))
    val localActorSystem = ActorSystem("LocalActorSystem")

    val remoteSimpleActor = localActorSystem.actorOf(Props[SimpleActorScala], name = "simpleActorScala")
//    val localSenderActor = localActorSystem.actorOf(Props[LocalActorScala], name="senderActor")

    remoteSimpleActor ! "Do it"

//
//    localSenderActor ! (remoteSimpleActor, "What's up")
//    Thread.sleep(5000)
//
//    //Grab the remote actor via actorSelection
//    localActorSystem.actorSelection("akka.tcp://RemoteActorSystem@127.0.0.1:10190/user/simpleActorJava") ! "Do it!"
//    Thread.sleep(5000)

//    remoteActorSystem.shutdown()
//    remoteActorSystem.awaitTermination()
  }
}
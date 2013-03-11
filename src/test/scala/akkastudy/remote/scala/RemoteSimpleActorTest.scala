package akkastudy.remote.scala

import org.scalatest.FlatSpec
import akka.actor.{Props, ActorSystem}
import akkastudy.loggingactor.scala.SimpleActorScala
import com.typesafe.config.ConfigFactory

class RemoteSimpleActorTest extends FlatSpec {
  behavior of "A simple actor"

  it should "receive our message in Scala but remotely" in {
    val config = ConfigFactory.load()
    val system = ActorSystem("RemoteActorSystem", config.getConfig("remote-system").withFallback(config))
    val myActor = system.actorOf(Props[SimpleActorScala], name = "simpleActorJava")
    myActor ! "Simple Test"
  }
}
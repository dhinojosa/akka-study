package akkastudy.cluster.scala


import java.util.concurrent.TimeUnit

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.FlatSpec

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class SimpleActorTest extends FlatSpec {

  behavior of "A simple actor calling a remote node"

  it should "receive our message in Scala" in {
    val config  = ConfigFactory.load()
    val system = ActorSystem("MySystem", config.getConfig("remote-system")
      .withFallback(config))

    val myActor = system.actorSelection("akka.tcp://My-Cluster@127.0.0.1:58451/user/SimpleActorCluster")

    myActor ! "Hello"
    myActor ! "Hello2"
    myActor ! "Hello3"
    myActor ! "Hello4"
    myActor ! "Hello5"

    Thread.sleep(10000)

    Await.result(system.terminate(), Duration(10, TimeUnit.SECONDS))
  }
}
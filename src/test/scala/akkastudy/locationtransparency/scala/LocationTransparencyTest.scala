package akkastudy.locationtransparency.scala

import org.scalatest.FlatSpec
import akka.actor.{Props, ActorSystem}
import com.typesafe.config.ConfigFactory
import akkastudy.remote.scala.{LocalActorScala, SimpleActorScala}
import akkastudy.locationtransparency.{ParentActor, ChildActor}

/**
 *
 * @author Daniel Hinojosa
 * @since 4/17/14 3:38 PM
 *        url: <a href="http://www.evolutionnext.com">http://www.evolutionnext.com</a>
 *        email: <a href="mailto:dhinojosa@evolutionnext.com">dhinojosa@evolutionnext.com</a>
 *        tel: 505.363.5832
 */
class LocationTransparencyTest extends FlatSpec {
  behavior of "Location Transparency"

  it should "have a parent and child actor where they can span across remote instances" in {
    val config = ConfigFactory.load()
    val remoteActorSystem = ActorSystem("RemoteActorSystem", config.getConfig("remote-system").withFallback(config))

    val localActorSystem = ActorSystem("LocalActorSystem")

    localActorSystem.actorOf(Props[ChildActor], name = "childActor")
    val parentActor = localActorSystem.actorOf(Props[ParentActor], name="parentActor")


    parentActor ! ("child", "cool!")
    Thread.sleep(10000)
    remoteActorSystem.shutdown()
    remoteActorSystem.awaitTermination()
    localActorSystem.shutdown()
    localActorSystem.awaitTermination()
  }
}

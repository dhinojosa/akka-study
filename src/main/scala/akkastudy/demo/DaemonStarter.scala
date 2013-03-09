package akkastudy.demo

import com.typesafe.config.ConfigFactory
import akka.actor.{Props, ActorSystem}
import akkastudy.simpleactor.scala.SimpleActorScala

/**
 * 
 * 
 * User: Daniel Hinojosa (dhinojosa@evolutionnext.com)
 * Date: 3/8/13
 * Time: 1:08 PM
 *
 */
object DaemonStarter extends App {
  val config = ConfigFactory.load()
  val system = ActorSystem("DaemonizedApp", config.getConfig("daemonized").withFallback(config))
  system.actorOf(Props[SimpleActorScala], name = "simpleActorScala")



}

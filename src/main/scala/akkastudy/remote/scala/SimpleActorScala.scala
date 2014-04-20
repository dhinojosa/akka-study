package akkastudy.remote.scala

import akka.actor.Actor
import akka.event.Logging

class SimpleActorScala extends Actor {
   val log = Logging(context.system, this)

   def receive = {
     case _ @ x ⇒
       context.parent.path.name
       log.info(s"Received Message in Simple Actor Scala: $x")
   }
 }
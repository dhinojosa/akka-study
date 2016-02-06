package akkastudy.simpleactor.scala

import akka.actor.Actor
import akka.event.Logging

class SimpleActorScala extends Actor {
   val log = Logging(context.system, this)

   def receive = {
     case x:String =>
       log.info("received message: " + x + " in Simple Actor Scala")
   }
 }
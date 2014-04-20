package akkastudy.remote.scala

import akka.actor.{Props, ActorLogging, Actor}

class SenderActorScala extends Actor with ActorLogging {

   def receive = {
     case x ⇒
       log.info("Received message" + x + " in Sender Actor Scala")
       log.info("creating simple actor")
       val simpleActorRef = context.system.actorOf(Props[SimpleActorScala], "simpleActorScala")
       simpleActorRef ! x
   }
 }
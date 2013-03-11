package akkastudy.becomeactor.scala

import akka.actor.Actor

class BecomeActor extends Actor {
   import context._

   def inRealBigTrouble:Receive = {
     case "spendTimeInPrison" => unbecome()
     case _ => sender ! "No"
   }

   def inTrouble:Receive = {
     case "askForgiveness" => unbecome()
     case "buyFlowers" => unbecome()
     case "getDrunkAndCrashCar" => become(inRealBigTrouble)
     case _ => sender ! "No"
   }

   def receive = {
     case "goHiking" => sender ! "Good hike!"
     case "goOutToDinner" => sender ! "Nom"
     case "getDrunkAndCrashCar" => become(inRealBigTrouble)
     case "useFoulLanguage" => become(inTrouble)
     case "leaveWithoutSayingThankYou" => become(inTrouble)
   }
}

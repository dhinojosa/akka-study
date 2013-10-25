package akkastudy.futureactor.scala

/**
 *
 * @author Daniel Hinojosa
 * @since 10/23/13 10:17 PM
 *        url: <a href="http://www.evolutionnext.com">http://www.evolutionnext.com</a>
 *        email: <a href="mailto:dhinojosa@evolutionnext.com">dhinojosa@evolutionnext.com</a>
 *        tel: 505.363.5832
 */

import akka.actor.Actor
import akka.event.Logging
import scala.concurrent.Future

class FuturesActor extends Actor {

  import context.dispatcher

  val log = Logging(context.system, this)

  def receive = {
    case "Compute" => {
      println("Starting: " + Thread.currentThread().getName)

      val f = Future {
           println("In the future: " + Thread.currentThread().getName)
           Thread.sleep(10000)
           14 * 3
       }

       f onSuccess {
         case x:Int => {
           println("In the success: " + Thread.currentThread().getName)
           println(s"Total is: $x")
         }
       }

       f onFailure {
         case x:Throwable => {
           println("In the failure: " + Thread.currentThread().getName)
           println(s"Failure msg: ${x.getMessage}")
         }
       }

       println("Ending")
    }

    case _ => {
      println("Processing Nothing")
    }
  }
}


package akkastudy.futures.scala

import org.scalatest.{Matchers, FlatSpec}
import concurrent.ExecutionContext
import java.util.concurrent.Executors
import akka.{util, actor}
import actor.{Props, ActorSystem}
import scala.concurrent.Future
import akka.util.Timeout
import scala.concurrent.duration._
import concurrent.Await

/**
 * Copyright 2013 Daniel Hinojosa
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 * akkastudy.futures.scala.FuturesTest are a suite of tests that
 * show various uses of Futures in Scala.
 */
class FuturesTest extends FlatSpec with Matchers {
  behavior of "An Actor System should"

  it should "A future is a object that has an answer but not quite yet delivered" in {
    //An execution context is required
    implicit val executionContext = ExecutionContext.
      fromExecutorService(Executors.newFixedThreadPool(12))

    val future = Future {
      "Hello" + " " + "World"
    }

    implicit val timeout = Timeout(5 seconds)


    println("Step 1")
    val result = Await.result(future, timeout.duration) //blocking
    println("Step 2: " + result)

    result should equal("Hello World")
  }

  it should "A future is a object that has an answer but not quite yet delivered, and can be read non-blocking" in {
    //An execution context is required
    implicit val executionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(12))

    val future = Future {
      "Hello" + " " + "World"
    }

    future foreach (x => println(s"I got an answer $x")) //asynchronous
    println("running1")
    println("running2")
    println("running3")
  }

  it should "A future is a object that can also be composed to form a complete answer" in {
    //An execution context is required
    implicit val executionContext = ExecutionContext.
      fromExecutorService(Executors.newFixedThreadPool(12))

    val future1 = Future {
      180 / 2
    }
    val future2 = Future {
      90 / 3
    }

    val result = future1.flatMap {
      x =>
        future2.map {
          y =>
            x + y
        }
    }
    println("Getting Ready to Run")
    result foreach (x => println("result: " + x))
    println("Doing Something in the Meantime")
  }


  it should "be able to ask information of an Actor, where as the the Actor will return the answer, blocked" in {
    import akka.pattern.ask

    implicit val timeout = util.Timeout(3 seconds)

    val system = ActorSystem("MySystem")
    val futureActor = system.actorOf(Props[FuturesActor], "futuresActor")
    val promise = futureActor ? "What is the meaning of love?"
    val result = Await.result(promise, timeout.duration) //Blocking
    println(result)
    Thread.sleep(10000)
  }
}

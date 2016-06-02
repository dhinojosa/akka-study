package akkastudy.futures.scala

import java.util.concurrent.Executors

import akka.util.Timeout
import org.scalatest.{FunSuite, Matchers}


import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future, Promise}

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
class FuturesTest extends FunSuite with Matchers {
  test("A future is a object that has an answer but not quite yet delivered") {
    //An execution context is required
    implicit val executionContext =
      ExecutionContext.
        fromExecutorService(Executors.newFixedThreadPool(12))

    val future = Future {
      Thread.sleep(2000)
      "Hello" + " " + "World"
    }

    implicit val timeout = Timeout(5 seconds)

    println("Step 1")
    val result = Await.result(future, timeout.duration) //blocking
    println("Step 2: " + result)

    result should equal("Hello World")
  }

  test("A future is a object that has an answer but not quite yet delivered, and can be read non-blocking") {
    //An execution context is required
    implicit val executionContext = ExecutionContext.
      fromExecutorService(Executors.newFixedThreadPool(12))

    val future = Future {
      println("I am on thread in a future: " + Thread.currentThread().getName)
      Thread.sleep(1000)
      "Hello" + " " + "World"
    }

    future foreach { x => println(s"I got an answer $x on foreach ${Thread.currentThread().getName}") } //asynchronous
    println("running1")
    println("running2")
    println("I am on thread: " + Thread.currentThread().getName)
    Thread.sleep(5000)
    println("running3")
  }

  test("A future is a object that can also be composed to form a complete answer") {
    //An execution context is required
    implicit val executionContext = ExecutionContext.
      fromExecutorService(Executors.newFixedThreadPool(12))

    val future1 = Future {
      Thread.sleep(3000)
      180 / 2
    }

    val future2 = Future {
      Thread.sleep(3000)
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
    Thread.sleep(3100)
  }

  test("show a promise example") {
    import ExecutionContext.Implicits.global

    val promise = Promise[String]()
    val future = promise.future
    future.foreach { x => println(x); x should be("Whoa") }
    Thread.sleep(2000)
    promise.success("Whoa")
    Thread.sleep(1000)
  }
}

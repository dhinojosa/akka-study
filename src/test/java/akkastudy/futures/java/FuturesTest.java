package akkastudy.futures.java;

import akka.dispatch.Futures;
import akka.dispatch.OnSuccess;
import akka.util.Timeout;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.ExecutionContext;
import scala.concurrent.ExecutionContext$;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.fest.assertions.Assertions.assertThat;

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
 * akkastudy.futures.java.FuturesTest are a suite of tests that
 * show various uses of Futures in Java.
 */

public class FuturesTest {

    /**
     * Simple test that show the sequential result of the output since Await.result blocks.  "Step 1" should show at the
     * command line first time, then the result of "Hello World" then "Step 2".  This should display always.
     * Finally the assertion will determine that we indeed got the result were looking for.
     *
     * @throws Exception
     */
    @Test
    public void testBasicFutures() throws Exception {
        ExecutionContext executionContext = ExecutionContext$.MODULE$.fromExecutorService(Executors.newFixedThreadPool(12));

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "Hello World";
            }
        };

        Future<String> future = Futures.future(callable, executionContext);

        Timeout timeout = new Timeout(Duration.create(5, "seconds"));

        System.out.println("Step 1");
        System.out.println(Await.result(future, timeout.duration())); //blocking
        System.out.println("Step 2");
    }

    public final static class PrintResult<T> extends OnSuccess<T> {
        @Override public final void onSuccess(T t) {
            System.out.println(t);
        }
    }

    @Test
    public void testAsynchronousCall() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(12);
        ExecutionContext executionContext = ExecutionContext$.MODULE$.fromExecutorService(executorService);

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "Hello World";
            }
        };

        Future<String> future = Futures.future(callable, executionContext);

        System.out.println("Step 1");
        future.onSuccess(new PrintResult<String>(), executionContext);
        System.out.println("Step 2");
    }
}

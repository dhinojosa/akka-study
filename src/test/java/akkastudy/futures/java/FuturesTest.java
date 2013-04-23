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

/**
 * A suite of tests that
 * show various uses of Futures in Java.
 */

public class FuturesTest {

    /**
     * Simple test that show the sequential result of the output since Await.result blocks.  "Step 1" should show at the
     * command line first time, then the result of "Hello World" then "Step 2".  This should display always.
     * Finally the assertion will determine that we indeed got the result were looking for.
     *
     * @throws Exception if the timeout lapses waiting for the result
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

    /**
     * Under the covers a Partial Function that waits for a success deliver of a future, when called will call the
     * {#onSuccess} method.
     *
     * @param <T> parameter type of the success that is to be delivered
     */
    public final static class PrintResult<T> extends OnSuccess<T> {
        @Override
        public final void onSuccess(T t) {
            System.out.println(t);
        }
    }

    /**
     * @throws Exception
     */
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

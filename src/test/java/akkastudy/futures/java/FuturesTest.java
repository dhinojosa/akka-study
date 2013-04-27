package akkastudy.futures.java;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.Futures;
import akka.dispatch.OnComplete;
import akka.pattern.Patterns;
import akka.util.Timeout;
import akkastudy.askactor.java.AskActor;
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
                return "Test Basic Futures: Hello World";
            }
        };

        Future<String> future = Futures.future(callable, executionContext);

        Timeout timeout = new Timeout(Duration.create(5, "seconds"));

        System.out.println("Test Basic Futures: Step 1");
        System.out.println(Await.result(future, timeout.duration())); //blocking
        System.out.println("Test Basic Futures: Step 2");
    }

    /**
     * Under the covers a Partial Function that waits for a success deliver of a future, when called will call the
     * {#onSuccess} method.
     *
     * @param <T> parameter type of the success that is to be delivered
     */
    public final static class PrintResult<T> extends OnComplete<T> {
        /**
         * This method will be invoked once when/if a Future that this callback is registered on
         * becomes completed with a failure or a success.
         * In the case of success then "failure" will be null, and in the case of failure the "success" will be null.
         */
        @Override
        public void onComplete(Throwable failure, T success) throws Throwable {
            if (failure == null) System.out.println(success.toString());
            else failure.printStackTrace();
        }
    }

    /**
     * Tests an asynchronous call using Scala Futures {@link scala.concurrent.Future} which is built upon the
     * {@link java.util.concurrent.ExecutorService}
     */
    @Test
    public void testAsynchronousCall() {
        ExecutorService executorService = Executors.newFixedThreadPool(12);
        ExecutionContext executionContext = ExecutionContext$.MODULE$.fromExecutorService(executorService);

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "Test Asynchronous Call: Hello World";
            }
        };

        Future<String> future = Futures.future(callable, executionContext);

        System.out.println("Test Asynchronous Call: Step 1");
        future.onComplete(new PrintResult<String>(), executionContext);
        System.out.println("Test Asynchronous Call: Step 2");
        System.out.println("Test Asynchronous Call: Step 3");
        System.out.println("Test Asynchronous Call: Step 4");
    }


    /**
     * Tests an asynchronous call to an actor, using ask method of Actor.  The ask method will return a future
     * which will eventually return a result, at which time the {@link PrintResult} class will print the result.
     */
    @Test
    public void testAskActorAsynchronouslyBlocked() {
        Timeout timeout = new Timeout(Duration.create(5, "seconds"));
        ActorSystem system = ActorSystem.create("MySystem");
        ActorRef actor = system.actorOf(new Props(AskActor.class), "askActor");
        Future<Object> future = Patterns.ask(actor, "Ping", timeout);
        System.out.println("Test Ask Actor Synchronous Call: Step 1");
        System.out.println("Test Ask Actor Synchronous Call: Step 2");
        future.onComplete(new PrintResult<>(), system.dispatcher());
        System.out.println("Test Ask Actor Synchronous Call: Step 3");
        System.out.println("Test Ask Actor Synchronous Call: Step 4");
    }

    /**
     * Tests a synchronous and call to an actor, using ask method of Actor.  The ask method will return a future
     * which will be piped to <code>Await.result</code>
     *
     * @throws Exception if thread times out or is interrupted
     */
    @Test
    public void testAskActorSynchronously() throws Exception {
        Timeout timeout = new Timeout(Duration.create(5, "seconds"));
        ActorSystem system = ActorSystem.create("MySystem");
        ActorRef actor = system.actorOf(new Props(AskActor.class), "askActor");
        Future<Object> future = Patterns.ask(actor, "Ping", timeout);
        System.out.println("Test Ask Actor Synchronous Call: Step 1");
        System.out.println("Test Ask Actor Synchronous Call: Step 2");
        System.out.println(Await.result(future, timeout.duration()));
        System.out.println("Test Ask Actor Synchronous Call: Step 3");
        System.out.println("Test Ask Actor Synchronous Call: Step 4");
    }
}

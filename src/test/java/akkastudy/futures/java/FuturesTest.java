package akkastudy.futures.java;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.ExecutionContexts;
import akka.dispatch.Futures;
import akka.dispatch.OnComplete;
import akka.dispatch.OnFailure;
import akka.dispatch.OnSuccess;
import akka.pattern.Patterns;
import akka.util.Timeout;
import akkastudy.askactor.java.AskActor;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.Promise;
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
    public void testJavaUtilConcurrentFutures() throws Exception {
        ExecutorService executorService =
                Executors.newCachedThreadPool();

        Callable<String> asynchronousTask
                = new Callable<String>() {
            @Override
            public String call() throws Exception {
                //something expensive
                Thread.sleep(5000);
                return "Asynchronous String Result";
            }
        };

        java.util.concurrent.Future<String> future =
                executorService.submit(asynchronousTask);
        System.out.println("Processing 1");
        System.out.println(future.get()); //waits if necessary
        System.out.println("Processing 2");
    }


    @Test
    public void testJavaUtilConcurrentFuturesAsynchronously() throws Exception {
        ExecutorService executorService =
                Executors.newCachedThreadPool();

        Callable<String> asynchronousTask = new Callable<String>() {
            @Override
            public String call() throws Exception {
                //something expensive
                Thread.sleep(500);
                return "Asynchronous String Result";
            }
        };

        java.util.concurrent.Future<String> future =
                executorService.submit(asynchronousTask);

        System.out.println("Processing Asynchronously 1");
        while (!future.isDone()) {
            //What to do while we are waiting
            System.out.println("Doing something else");
        }
        System.out.println(future.get()); //immediate
        System.out.println("Processing Asynchronously 2");
    }


    @Test
    public void testBasicScalaFutures() throws Exception {
        ExecutionContext executionContext =
                ExecutionContexts.fromExecutorService
                        (Executors.newFixedThreadPool(12));

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(4000);
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
     * Tests an asynchronous call using Scala Futures {@link scala.concurrent.Future} which is built upon the
     * {@link java.util.concurrent.ExecutorService}
     */
    @Test
    public void testAsynchronousCall() throws InterruptedException {
        ExecutorService executorService =
                Executors.newFixedThreadPool(3);
        ExecutionContext executionContext =
                ExecutionContexts.fromExecutorService(executorService);

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(5000);
                return "Test Asynchronous Call: Hello World";
            }
        };

        Future<String> future = Futures.future(callable, executionContext);

        System.out.println("Test Asynchronous Call: Step 1");
        future.onComplete(new OnComplete<String>() {
            @Override
            public void onComplete(Throwable failure, String success) throws Throwable {
                if (failure != null) System.out.println("Failure:" + failure.getMessage());
                else System.out.println("Success: " + success);
            }
        }, executionContext);

        System.out.println("Test Asynchronous Call: Step 2");
        System.out.println("Test Asynchronous Call: Step 3");
        System.out.println("Test Asynchronous Call: Step 4");
        Thread.sleep(10000);
    }

    @Test
    public void testPromises() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(12);
        ExecutionContext executionContext = ExecutionContexts.fromExecutorService(executorService);

        class MyCallable implements Callable<Integer> {

            private final Promise<String> promise;

            public MyCallable(Promise<String> promise) {
                this.promise = promise;
            }

            @Override
            public Integer call() throws Exception {
                Thread.sleep(3000);
                promise.success("So far so good");
                Thread.sleep(3000);
                return 900;
            }
        }

        Promise<String> promise = Futures.promise();
        Future<Integer> future = Futures.
                future(new MyCallable(promise), executionContext);
        Future<String> promisedFuture = promise.future();

        future.onSuccess(new OnSuccess<Integer>() {
            @Override
            public void onSuccess(Integer result) throws Throwable {
                System.out.format("Received a response for my future: %s\n",
                        result);
            }
        }, executionContext);

        OnFailure onFailure = new OnFailure() {
            @Override
            public void onFailure(Throwable failure) throws Throwable {
                failure.printStackTrace();
            }
        };
        future.onFailure(onFailure, executionContext);

        promisedFuture.onSuccess(new OnSuccess<String>() {
            @Override
            public void onSuccess(String result) throws Throwable {
                System.out.format("Received a response for my promise: %s\n", result);
            }
        }, executionContext);

        promisedFuture.onFailure(onFailure, executionContext);
        Thread.sleep(10000);
    }

    //
//    /**
//     * Tests an asynchronous call to an actor, using ask method of Actor.  The ask method will return a future
//     * which will eventually return a result, at which time the {@link PrintResult} class will print the result.
//     */
    @Test
    public void testAskActorAsynchronouslyNonBlocked() throws InterruptedException {
        OnComplete<Object> onComplete = new OnComplete<Object>() {
            @Override
            public void onComplete(Throwable failure, Object success) throws Throwable {
                if (success != null) System.out.format
                        ("Got the answer: %s\n", success);
                else failure.printStackTrace();
            }
        };

        Timeout timeout = new Timeout(Duration.create(5, "seconds"));
        ActorSystem system = ActorSystem.create("MySystem");
        ActorRef actor = system.actorOf(Props.create(AskActor.class),
                "askActor");

        Future<Object> future = Patterns.ask(actor, "Ping", timeout);
        future.onComplete(onComplete, system.dispatcher());

        System.out.println("Test Ask Actor Asynchronous Call: Step 1");
        System.out.println("Test Ask Actor Asynchronous Call: Step 2");
        System.out.println("Test Ask Actor Asynchronous Call: Step 3");
        System.out.println("Test Ask Actor Asynchronous Call: Step 4");
        Thread.sleep(5000);

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
        ActorRef actor = system.actorOf(Props.create(AskActor.class), "askActor");
        Future<Object> future = Patterns.ask(actor, "Ping", timeout);
        System.out.println("Test Ask Actor Synchronous Call: Step 1");
        System.out.println("Test Ask Actor Synchronous Call: Step 2");
        System.out.println(Await.result(future, timeout.duration()));
        System.out.println("Test Ask Actor Synchronous Call: Step 3");
        System.out.println("Test Ask Actor Synchronous Call: Step 4");
    }
}

package akkastudy.supervisorstrategy.java;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.OnComplete;
import akka.pattern.Patterns;
import akka.util.Timeout;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

import static org.fest.assertions.Assertions.assertThat;


public class SupervisorStrategyTest {

    /**
     * Create any child actor and throw an Illegal Argument Exception
     *
     * @throws InterruptedException
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testActor() throws Exception {
        ActorSystem system = ActorSystem.create("MySystem");

        Timeout timeout = new Timeout(5, TimeUnit.SECONDS);

        ActorRef grandparent = system.actorOf(
                Props.create(OneForOneGrandparentActor.class),
                "GrandparentActorJava");

        Future<Object> childActorFuture1 = Patterns.ask
                (grandparent, Props.create(ExceptionalChildActor.class),
                        timeout);

        Thread.sleep(3000);

        childActorFuture1.onComplete(
                new OnComplete<Object>() {
                    @Override
                    public void onComplete(Throwable failure, Object actorRef)
                            throws Throwable {
                        ((ActorRef) actorRef).tell("IllegalArgumentException", null);
                    }
                }, system.dispatcher()
        );


        Thread.sleep(13000);
        System.out.println("Shutting Down Server");
        Await.result(system.terminate(), Duration.apply(10, TimeUnit.SECONDS));
    }

    /**
     * Create any child actor and throw a NullPointerException, then sends a another message
     * since the actor has been restarted
     *
     * @throws InterruptedException
     */
    @Test
    public void testActorWithAFactory() throws Exception {
        final ActorSystem system = ActorSystem.create("MySystem");

        final Timeout timeout = new Timeout(5, TimeUnit.SECONDS);

        ActorRef grandparent = system.actorOf(Props.create(OneForOneGrandparentActor.class), "GrandparentActorScala");
        Future<Object> childActorFuture1 = Patterns.ask(grandparent, Props.create(ExceptionalChildActor.class), timeout);
        grandparent.tell(Props.create(ExceptionalChildActor.class), null);
        grandparent.tell(Props.create(ExceptionalChildActor.class), null);

        Thread.sleep(3000);

        childActorFuture1.onComplete(
                new OnComplete<Object>() {
                    @Override
                    public void onComplete(Throwable failure, Object success) throws Throwable {
                        ActorRef actorRef = (ActorRef) success;
                        actorRef.tell("NullPointerException", null);
                        Future<Object> childActorFuture2 = Patterns.ask(actorRef, "OK", timeout);
                        childActorFuture2.onComplete(
                                new OnComplete<Object>() {
                                    @Override
                                    public void onComplete(Throwable failure, Object success) throws Throwable {
                                        assertThat((String) success).isEqualTo("Message Received OK");
                                    }
                                }, system.dispatcher()
                        );
                    }
                }, system.dispatcher()
        );
        Thread.sleep(15000);
        System.out.println("Shutting down the server");
        Await.result(system.terminate(), Duration.apply(10, TimeUnit.SECONDS));
    }

    /**
     * Resume the child actor if an arithmetic exception is thrown as
     * specified in the strategy at the parent
     *
     * @throws InterruptedException
     */
    @Test
    public void testActor3() throws Exception {
        ActorSystem system = ActorSystem.create("MySystem");

        Timeout timeout = new Timeout(5, TimeUnit.SECONDS);

        ActorRef grandparent = system.actorOf(Props.create(OneForOneGrandparentActor.class), "GrandparentActorScala");

        grandparent.tell(Props.create(ExceptionalChildActor.class), null);
        grandparent.tell(Props.create(ExceptionalChildActor.class), null);
        grandparent.tell(Props.create(ExceptionalChildActor.class), null);

        Thread.sleep(3000);

        Future<Object> childActorFuture1 = Patterns.ask(grandparent, Props.create(ExceptionalChildActor.class), timeout);

        childActorFuture1.onComplete(
                new OnComplete<Object>() {
                    @Override
                    public void onComplete(Throwable failure, Object success) throws Throwable {
                        ((ActorRef) success).tell("ArithmeticException", null);
                    }
                }, system.dispatcher()
        );

        Thread.sleep(15000);
        System.out.println("Shutting down the server");
        Await.result(system.terminate(), Duration.apply(10, TimeUnit.SECONDS));
    }

    /**
     * Child actor restarted, not because of the parent, which has escalate for Null Pointer Exceptions,
     * but because of the grandparent
     *
     * @throws InterruptedException
     */
    @Test
    public void testActor4() throws Exception {
        final ActorSystem system = ActorSystem.create("MySystem");

        final Timeout timeout = new Timeout(5, TimeUnit.SECONDS);

        ActorRef grandparent = system.actorOf(Props.create(OneForOneGrandparentActor.class), "GrandparentActorScala");


        grandparent.tell(Props.create(ExceptionalChildActor.class), null);
        grandparent.tell(Props.create(ExceptionalChildActor.class), null);
        grandparent.tell(Props.create(ExceptionalChildActor.class), null);

        Future<Object> parentActorFuture = Patterns.ask(grandparent, Props.create(OneForOneParentActor.class), timeout);


        Thread.sleep(3000);

        parentActorFuture.onComplete(
                new OnComplete<Object>() {
                    @Override
                    public void onComplete(Throwable failure, Object success) throws Throwable {
                        ActorRef parentActorFutureRef = (ActorRef) success;
                        Future<Object> childActorFutureRef = Patterns.ask
                                (parentActorFutureRef, Props.create(ExceptionalChildActor.class), timeout);
                        childActorFutureRef.onComplete(new OnComplete<Object>() {
                            @Override
                            public void onComplete(Throwable failure, Object success) throws Throwable {
                                ActorRef childActorRef = (ActorRef) success;
                                childActorRef.tell("NullPointerException", null);
                                Future<Object> futureMessage = Patterns.ask(childActorRef, "OK", timeout);
                                futureMessage.onComplete(
                                        new OnComplete<Object>() {
                                            @Override
                                            public void onComplete(Throwable failure, Object success) throws Throwable {
                                                assertThat((String) success).isEqualTo("Message Received: OK");
                                            }
                                        }
                                        , system.dispatcher());
                            }
                        }, system.dispatcher());
                    }
                }, system.dispatcher()
        );

        Thread.sleep(15000);
        System.out.println("Shutting down server");
        Await.result(system.terminate(), Duration.apply(10, TimeUnit.SECONDS));
    }

    /**
     * Using a One for One Grandparent Actor, and an All for One Parent,
     * all the parent's will share the same fate
     *
     * @throws InterruptedException
     */
    @Test
    public void test5() throws Exception {
        final ActorSystem system = ActorSystem.create("MySystem");

        final Timeout timeout = new Timeout(60, TimeUnit.SECONDS);

        ActorRef grandparent = system.actorOf(Props.create(OneForOneGrandparentActor.class), "GrandparentActorScala");


        grandparent.tell(Props.create(ExceptionalChildActor.class), null);
        grandparent.tell(Props.create(ExceptionalChildActor.class), null);
        grandparent.tell(Props.create(ExceptionalChildActor.class), null);

        Future<Object> parentActorFuture = Patterns.ask(grandparent, Props.create(AllForOneParentActor.class), timeout);

        Thread.sleep(3000);

        parentActorFuture.onComplete(
                new OnComplete<Object>() {
                    @Override
                    public void onComplete(Throwable failure, Object success) throws Throwable {
                        ActorRef parentActorFutureRef = (ActorRef) success;

                        Future<Object> childActorFutureRef = Patterns.ask
                                (parentActorFutureRef, Props.create(ExceptionalChildActor.class), timeout);


                        childActorFutureRef.onComplete(new OnComplete<Object>() {
                            @Override
                            public void onComplete(Throwable failure, Object success) throws Throwable {
                                ActorRef childActorRef = (ActorRef) success;
                                childActorRef.tell("NullPointerException", null);
                                Future<Object> futureMessage = Patterns.ask(childActorRef, "OK", timeout);
                                futureMessage.onComplete(
                                        new OnComplete<Object>() {
                                            @Override
                                            public void onComplete(Throwable failure, Object success) throws Throwable {
                                                System.out.println("F" + failure);
                                                assertThat((String) success).isEqualTo("Message Received: OK");
                                            }
                                        }, system.dispatcher());
                            }
                        }, system.dispatcher());
                    }
                }, system.dispatcher()
        );

        Thread.sleep(15000);
        System.out.println("Shutting down server");
        Await.result(system.terminate(), Duration.apply(10, TimeUnit.SECONDS));
    }
}

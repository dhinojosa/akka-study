package akkastudy.simpleactor.java;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.Creator;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class SimpleActorTest {

    @Test
    public void testActor() throws Exception {
        ActorSystem system = ActorSystem.create("MySystem");
        ActorRef myActor = system.actorOf(
                Props.create(SimpleActorJava.class),
                "simpleActorJava");


        System.out.println(Thread.currentThread().getName());
        myActor.tell("Bueno!", system.deadLetters());
        Thread.sleep(10000);
        Await.result(system.terminate(), Duration.apply(10, TimeUnit.SECONDS));

    }

    @Test
    public void testActorWithAFactory() throws Exception {
        ActorSystem system = ActorSystem.create("MySystem");
        ActorRef myActor = system.actorOf(
                Props.create(SimpleActorJava.class,
                        new Creator<SimpleActorJava>() {
                    @Override
                    public SimpleActorJava create() throws Exception {
                        return new SimpleActorJava();
                    }
                }), "simpleActorJava"
        );
        myActor.tell("Bueno!", system.deadLetters());
        Thread.sleep(10000);
        Await.result(system.terminate(), Duration.apply(10, TimeUnit.SECONDS));
    }

    @Test
    public void testActorSelection() throws Exception {
        ActorSystem system = ActorSystem.create("MySystem");
        system.actorOf(
                Props.create(SimpleActorJava.class),
                "simpleActorJava");

        Thread.sleep(2000);

        System.out.println
                (Thread.currentThread().getName());

        ActorSelection myActor =
                system.actorSelection
                        ("akka://MySystem/user/simpleActorJava");

        myActor.tell("Bueno!", system.deadLetters());
        Thread.sleep(4000);
        Await.result(system.terminate(), Duration.apply(10, TimeUnit.SECONDS));
    }
}

package akkastudy.simpleactor.java;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class ActorDiscussionTest {

    @Test
    public void testActorSelection() throws Exception {
        ActorSystem system = ActorSystem.create("MySystem");

        system.actorOf(
                Props.create(BenAffleckJava.class), "benAffleckJava");
        system.actorOf(
                Props.create(MattDamonJava.class), "mattDamonJava");

        Thread.sleep(2000);
        ActorSelection benAffleckRef =
                system.actorSelection
                        ("akka://MySystem/user/benAffleckJava");

        benAffleckRef.tell("Ask Matt!", system.deadLetters());
        Thread.sleep(10000);
        Await.result(system.terminate(), Duration.apply(10, TimeUnit.SECONDS));
    }
}

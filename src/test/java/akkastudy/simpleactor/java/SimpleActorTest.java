package akkastudy.simpleactor.java;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.junit.Test;

public class SimpleActorTest {

    @Test
    public void testActor() {
        ActorSystem system = ActorSystem.create("MySystem");
        ActorRef myActor = system.actorOf(
                new Props(SimpleActorJava.class),
                "simpleActorJava");
        ActorRef deadLettersActor = system.actorFor("/deadLetters");
        myActor.tell("Bueno!", deadLettersActor);
    }
}

package akkastudy.simpleactor.java;

import akka.actor.*;
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

    @Test
    public void testActorWithAFactory() {
        ActorSystem system = ActorSystem.create("MySystem");
        ActorRef myActor = system.actorOf(new Props(new UntypedActorFactory() {
            @Override
            public Actor create() throws Exception {
                return new SimpleActorJava();
            }
        }), "simpleActorJava");
        ActorRef deadLettersActor = system.actorFor("/deadLetters");
        myActor.tell("Bueno!", deadLettersActor);
    }
}

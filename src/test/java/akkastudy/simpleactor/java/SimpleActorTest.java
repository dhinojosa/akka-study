package akkastudy.simpleactor.java;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.Creator;
import org.junit.Test;

public class SimpleActorTest {

    @Test
    public void testActor() {
        ActorSystem system = ActorSystem.create("MySystem");
        ActorRef myActor = system.actorOf(
                Props.create(SimpleActorJava.class),
                "simpleActorJava");
        ActorSelection deadLettersActor = system.actorSelection("/deadLetters");
        myActor.tell("Bueno!", deadLettersActor.anchor());
    }

    @Test
    public void testActorWithAFactory() {
        ActorSystem system = ActorSystem.create("MySystem");
        ActorRef myActor = system.actorOf(
                Props.create(SimpleActorJava.class, new Creator<SimpleActorJava>() {
                    @Override
                    public SimpleActorJava create() throws Exception {
                        return new SimpleActorJava();
                    }
                }), "simpleActorJava"
        );
        ActorSelection deadLettersActorSelection = system.actorSelection("/deadLetters");
        myActor.tell("Bueno!", deadLettersActorSelection.anchor());
    }
}

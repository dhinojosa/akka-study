package akkastudy.eventstream.java;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.DeadLetter;
import akka.actor.Props;
import org.junit.Test;

public class EventStreamTest {

    @Test
    public void testEventStreamWithDeadLetters() {
        ActorSystem system = ActorSystem.create("MySystem");
        ActorRef actor = system.actorOf(new Props(MyDeadLetterActorListener.class));
        system.eventStream().subscribe(actor, DeadLetter.class);
        ActorRef nonExistentActor =
                system.actorFor("akka://MySystem/user/somethingElseIShouldn\'tBeLookingFor");
        nonExistentActor.tell("Bueno!");
    }
}

package akkastudy.eventstream.java;

import akka.actor.*;
import org.junit.Test;

public class EventStreamTest {

    @Test
    public void testEventStreamWithDeadLetters() {
        ActorSystem system = ActorSystem.create("MySystem");
        ActorRef actor = system.actorOf(Props.create(MyDeadLetterActorListener.class));
        system.eventStream().subscribe(actor, DeadLetter.class);
        ActorSelection nonExistentActorSelection =
                system.actorSelection("akka://MySystem/user/somethingElseIShouldn\'tBeLookingFor");
        nonExistentActorSelection.tell("Bueno!", null);
    }
}

package akkastudy.eventstream.java;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.DeadLetter;
import akka.actor.Props;
import org.junit.Test;

/**
 * User: Daniel Hinojosa (dhinojosa@evolutionnext.com)
 * Date: 3/9/13
 * Time: 12:18 PM
 */
public class EventStreamTest {

    @Test
    public void testEventStreamWithDeadLetters() {
        ActorSystem system = ActorSystem.create("MySystem");
        ActorRef actor = system.actorOf(new Props(DeadLetterActor.class));
        system.eventStream().subscribe(actor, DeadLetter.class);
        ActorRef nonExistentActor =
                system.actorFor("akka://MySystem/user/somethingElseIShouldn\'tBeLookingFor");
        nonExistentActor.tell("Bueno!");
    }
}

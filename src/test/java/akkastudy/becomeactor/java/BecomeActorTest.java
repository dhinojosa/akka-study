package akkastudy.becomeactor.java;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akkastudy.remote.java.SenderActorJava;
import org.junit.Test;

/**
 * @author Daniel Hinojosa
 * @since 3/24/14 3:35 PM
 * url: <a href="http://www.evolutionnext.com">http://www.evolutionnext.com</a>
 * email: <a href="mailto:dhinojosa@evolutionnext.com">dhinojosa@evolutionnext.com</a>
 * tel: 505.363.5832
 */
public class BecomeActorTest {
    @Test
    public void testBasicAsynchronousAsk() throws InterruptedException {
        ActorSystem system = ActorSystem.create("MySystem");
        ActorRef becomeActor = system.actorOf(Props.create(BecomeActor.class), "becomeActor");
        ActorRef senderActor = system.actorOf(Props.create(SenderActorJava.class), "senderActor");

        becomeActor.tell("goHiking", senderActor);
        becomeActor.tell("goHiking", senderActor);
        becomeActor.tell("useFoulLanguage", senderActor);
        becomeActor.tell("goHiking", senderActor); //No
        becomeActor.tell("getDrunkAndCrashCar", senderActor); //No
        becomeActor.tell("goHiking", senderActor); //No
        becomeActor.tell("askForgiveness", senderActor); //No
        becomeActor.tell("spendTimeInPrison", senderActor); //No
        becomeActor.tell("goOutToDinner", senderActor); //Nom
        becomeActor.tell("askForgiveness", senderActor); //No Response
        becomeActor.tell("goHiking", senderActor); //Good Hike!
    }
}

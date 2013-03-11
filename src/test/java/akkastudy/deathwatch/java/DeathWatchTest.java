package akkastudy.deathwatch.java;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akkastudy.SenderActor;
import org.junit.Test;

/**
 * User: Daniel Hinojosa (dhinojosa@evolutionnext.com)
 * Date: 3/9/13
 * Time: 12:18 PM
 */
public class DeathWatchTest {

    @Test
    public void testDeathWatch() throws InterruptedException {
        ActorSystem system = ActorSystem.create("MySystem");
        ActorRef actor = system.actorOf(new Props(DeathWatchActor.class), "deathWatcher");
        ActorRef sender = system.actorOf(new Props(SenderActor.class), "senderActor");
        Thread.sleep(3000);
        actor.tell("kill", sender);
        Thread.sleep(3000);
    }
}

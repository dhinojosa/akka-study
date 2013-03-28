package akkastudy.askactor.java;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.OnSuccess;
import akka.util.Timeout;
import akkastudy.SenderActor;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import static akka.pattern.Patterns.ask;

/**
 * Created by Daniel Hinojosa
 * User: Daniel Hinojosa
 * Date: 3/27/13
 * Time: 8:26 PM
 * url: <a href="http://www.evolutionnext.com">http://www.evolutionnext.com</a>
 * email: <a href="mailto:dhinojosa@evolutionnext.com">dhinojosa@evolutionnext.com</a>
 * tel: 505.363.5832
 */
public class AskActorTest {

    /**
     * Test a basic actor using ask which will return a <code>Future</code>
     */
    @Test
    public void testBasicAsynchronousAsk() throws InterruptedException {
        ActorSystem system = ActorSystem.create("MySystem");
        ActorRef actor = system.actorOf(new Props(AskActor.class), "askActor");
        ActorRef sender = system.actorOf(new Props(SenderActor.class), "senderActor");
        Future<Object> future = ask(actor, "Ping", 4000);
        future.onSuccess(new OnSuccessHandler<>(), system.dispatcher());
    }

    @Test
    public void testBasicSynchronousAsk() throws Exception {
        ActorSystem system = ActorSystem.create("MySystem");
        ActorRef actor = system.actorOf(new Props(AskActor.class), "askActor");
        ActorRef sender = system.actorOf(new Props(SenderActor.class), "senderActor");
        Future<Object> future = ask(actor, "Ping", 4000);
        Await.result(future, Duration.create(5, "seconds"));
    }
}

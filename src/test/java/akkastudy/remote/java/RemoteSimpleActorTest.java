package akkastudy.remote.java;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.util.Timeout;
import akkastudy.simpleactor.java.SimpleActorJava;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.Future;

import java.util.concurrent.TimeUnit;

public class RemoteSimpleActorTest {

    @Test
    public void testActor() throws Exception {
        Config config = ConfigFactory.load();

        ActorSystem remoteSystem = ActorSystem.create("RemoteSystem",
                config.getConfig("remote-system").withFallback(config));
        ActorSystem localSystem = ActorSystem.create("LocalSystem");

        ActorRef remoteActor = remoteSystem.actorOf(Props.create(SimpleActorJava.class), "simpleActorJava");
        ActorRef senderActor = localSystem.actorOf(Props.create(SenderActor.class), "senderActorJava");

        ActorSelection deadLettersActorSelection = localSystem.actorSelection("/deadLetters");
        Future<ActorRef> actorRefFuture = deadLettersActorSelection.resolveOne(Timeout.apply(5000));

        ActorRef deadLettersActorRef = Await.result(
                actorRefFuture,
                new Timeout(5000, TimeUnit.MILLISECONDS).duration());

        senderActor.tell(remoteActor, deadLettersActorRef);
        remoteSystem.shutdown();
        remoteSystem.awaitTermination();
    }
}

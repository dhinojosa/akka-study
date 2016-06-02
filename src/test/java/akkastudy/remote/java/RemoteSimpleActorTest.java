package akkastudy.remote.java;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class RemoteSimpleActorTest {

    @Test
    public void testActor() throws Exception {
        Config config = ConfigFactory.load(); //load conf

        ActorSystem remoteSystem = ActorSystem.create("remoteSystem",
                config.getConfig("remote-system").withFallback(config));


        ActorRef simpleActorJava = remoteSystem.actorOf
                (Props.create(SimpleActorJava.class), "simpleActorJava");

        simpleActorJava.tell("Send Message", remoteSystem.deadLetters());

        Thread.sleep(10000);
        Await.result(remoteSystem.terminate(), Duration.apply(10, TimeUnit.SECONDS));
    }
}

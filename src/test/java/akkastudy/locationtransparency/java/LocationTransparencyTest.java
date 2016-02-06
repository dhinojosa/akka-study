package akkastudy.locationtransparency.java;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akkastudy.remote.java.SenderActorJava;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class LocationTransparencyTest {

    @Test
    public void testLocationTransparencyWithTwoRemoteActorSystemsOneActor() throws Exception {
        Config config = ConfigFactory.load();

        ActorSystem remoteSystemBeta = ActorSystem.create("actorSystemBeta",
                config.getConfig("remote-system-beta").withFallback(config));

        ActorSystem remoteSystemAlpha = ActorSystem.create("actorSystemAlpha",
                config.getConfig("remote-system-alpha").withFallback(config));


        ActorRef simpleActorJava = remoteSystemAlpha.actorOf(Props.create(SimpleActorJava.class), "simpleActorJava");

        simpleActorJava.tell("Hello, there", remoteSystemAlpha.deadLetters());
        Thread.sleep(3000);
        Await.result(remoteSystemAlpha.terminate(), Duration.apply(10, TimeUnit.SECONDS));
        Await.result(remoteSystemBeta.terminate(), Duration.apply(10, TimeUnit.SECONDS));
    }

    @Test
    public void testLocationTransparencyWithTwoRemoteActorSystemsOneActorForwardingToAnother() throws Exception {
        Config config = ConfigFactory.load();

        ActorSystem remoteSystemBeta = ActorSystem.create("actorSystemBeta",
                config.getConfig("remote-system-beta").withFallback(config));

        ActorSystem remoteSystemAlpha = ActorSystem.create("actorSystemAlpha",
                config.getConfig("remote-system-alpha").withFallback(config));


        ActorRef senderActorJava = remoteSystemAlpha.actorOf(Props.create(SenderActorJava.class), "senderActorJava");

        senderActorJava.tell("Hello, there", remoteSystemAlpha.deadLetters());
        Thread.sleep(15000);
        System.out.println("Shutting down servers");
        Await.result(remoteSystemAlpha.terminate(), Duration.apply(10, TimeUnit.SECONDS));
        Await.result(remoteSystemBeta.terminate(), Duration.apply(10, TimeUnit.SECONDS));
    }
}

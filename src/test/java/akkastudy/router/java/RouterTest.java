package akkastudy.router.java;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.FromConfig;
import akkastudy.SenderActor;
import akkastudy.simpleactor.java.SimpleActorJava;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class RouterTest {

    @Test
    public void testRouterFromConfiguration() throws Exception {
        Config config = ConfigFactory.load();

        ActorSystem actorSystem = ActorSystem.create("MySystem",
                config.getConfig("routing-system").withFallback(config));

        ActorRef router = actorSystem.
                actorOf(Props.create(SimpleActorJava.class)
                        .withRouter(FromConfig.getInstance()), "simplerouter");

        ActorRef senderActor = actorSystem.actorOf
                (Props.create(SenderActor.class));

        router.tell("test", senderActor);
        router.tell("test", senderActor);
        router.tell("test", senderActor);
        router.tell("test", senderActor);
        router.tell("test", senderActor);
        router.tell("test", senderActor);
        router.tell("test", senderActor);
        router.tell("test", senderActor);
        router.tell("test", senderActor);
        router.tell("test", senderActor);
        router.tell("test", senderActor);

        Thread.sleep(15000);
        Await.result(actorSystem.terminate(),
                Duration.apply(10, TimeUnit.SECONDS));

    }
}

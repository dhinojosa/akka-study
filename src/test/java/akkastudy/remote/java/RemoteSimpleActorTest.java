package akkastudy.remote.java;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akkastudy.simpleactor.java.SimpleActorJava;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;

public class RemoteSimpleActorTest {

    @Test
    public void testActor() throws Exception {
        Config config = ConfigFactory.load();

//        ActorSystem remoteSystem = ActorSystem.create("remoteActorSystem",
//                config.getConfig("remote-system").withFallback(config));
//        ActorSystem localSystem = ActorSystem.create("localActorSystem",
//                config.getConfig("local-remote-binding-system").withFallback(config));


        ActorSystem remoteSystem = ActorSystem.create("actorSystemBeta",
                config.getConfig("remote-system-beta").withFallback(config));

        ActorSystem localSystem = ActorSystem.create("actorSystemAlpha",
                ConfigFactory.load("remote-system-alpha"));


        ActorRef senderActor = localSystem.actorOf(Props.create(SenderActor.class), "senderActorJava");

        Thread.sleep(10000);
        localSystem.shutdown();
        localSystem.awaitTermination();
        remoteSystem.shutdown();
        remoteSystem.awaitTermination();
    }
}

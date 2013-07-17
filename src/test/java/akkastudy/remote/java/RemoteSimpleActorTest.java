package akkastudy.remote.java;

import akka.actor.*;
import akkastudy.simpleactor.java.SimpleActorJava;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;

public class RemoteSimpleActorTest {
    @Test
    public void testActor() {
        Config config = ConfigFactory.load();
        ActorSystem system = ActorSystem.create("MySystem", config.getConfig("remote-system").withFallback(config));

        ActorRef myActor = system.actorOf(
                new Props(SimpleActorJava.class),
                "simpleActorJava");
        ActorRef deadLettersActor = system.actorFor("/deadLetters");
        myActor.tell("Bueno!", deadLettersActor);
    }
}

package akkastudy.typedactor.java;

import akka.actor.ActorSystem;
import akka.actor.TypedActor;
import akka.actor.TypedProps;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

import static org.fest.assertions.Assertions.assertThat;

public class TypedActorTest {

    @Test
    public void testActor() throws Exception {
        ActorSystem system = ActorSystem.create("MySystem");
        RegistrationActor registrationActor =
          TypedActor.get(system).typedActorOf(
                  new TypedProps<>
              (RegistrationActor.class, RegistrationActorImpl.class),
                  "registrationActor");

        registrationActor.registerPerson(new Person("Abraham", "Lincoln"));
        assertThat(registrationActor.getCount()).isEqualTo(1);
        registrationActor.registerPerson(new Person("George", "Washington"));
        assertThat(registrationActor.getCount()).isEqualTo(2);
        Thread.sleep(5000);
        Await.result(system.terminate(), Duration.apply(10, TimeUnit.SECONDS));


    }
}

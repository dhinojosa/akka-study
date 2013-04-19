package akkastudy.typedactor.java;

import akka.actor.ActorSystem;
import akka.actor.TypedActor;
import akka.actor.TypedProps;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class TypedActorTest {

    @Test
    public void testActor() {
        ActorSystem system = ActorSystem.create("MySystem");
        RegistrationActor registrationActor =
          TypedActor.get(system).typedActorOf(
            new TypedProps<RegistrationActorImpl>
              (RegistrationActor.class, RegistrationActorImpl.class),
                  "registrationActor");
        TypedActor.get(system).typedActorOf(
                new TypedProps<RegistrationActorImpl>
                        (RegistrationActor.class, RegistrationActorImpl.class),
                "registrationActor");
        registrationActor.registerPerson(new Person("Abraham", "Lincoln"));
        assertThat(registrationActor.getCount()).isEqualTo(1);
        registrationActor.registerPerson(new Person("George", "Washington"));
        assertThat(registrationActor.getCount()).isEqualTo(2);
    }
}

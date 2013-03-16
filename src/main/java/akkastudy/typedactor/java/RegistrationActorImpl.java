package akkastudy.typedactor.java;

import akka.actor.TypedActor;

import java.util.ArrayList;
import java.util.List;

    public class RegistrationActorImpl implements RegistrationActor, TypedActor.PreStart {

        private List<Person> people;

        @Override
        public void preStart() {
            this.people = new ArrayList<>();
        }

        @Override
        public void registerPerson(Person person) {
            this.people.add(person);
        }

        @Override
        public int getCount() {
            return people.size();
        }
    }

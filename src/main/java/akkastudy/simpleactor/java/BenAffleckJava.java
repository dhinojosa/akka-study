package akkastudy.simpleactor.java;

import akka.actor.ActorSelection;
import akka.actor.PoisonPill;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class BenAffleckJava extends UntypedActor {
    LoggingAdapter log =
            Logging.getLogger(getContext().system(), this);

    public void onReceive(Object message) {
        ActorSelection selection = getContext().actorSelection("../mattDamonJava");
        if (message instanceof String) {
            log.info("Ben: Sending message to Matt Damon");

            selection.tell
                    ("Hello, Matt, how many fingers am I holding up?",
                            self());
        } else if (message instanceof Integer){
            log.info("Ben: Matt Damon gave me {}", message);
        }
        unhandled(message);
    }
}

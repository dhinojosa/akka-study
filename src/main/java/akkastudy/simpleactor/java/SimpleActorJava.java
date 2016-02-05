package akkastudy.simpleactor.java;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class SimpleActorJava extends UntypedActor {
    LoggingAdapter log =
            Logging.getLogger(getContext().system(), this);

    public void onReceive(Object message) {
        if (message instanceof String)
            log.info("Received String message " +
                    "in SimpleActorJava {}", message);
        else
            unhandled(message);
    }
}

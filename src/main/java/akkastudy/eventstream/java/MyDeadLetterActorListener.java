package akkastudy.eventstream.java;

import akka.actor.DeadLetter;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class MyDeadLetterActorListener extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public void onReceive(Object message) {
        if (message instanceof DeadLetter) {
            DeadLetter deadLetter = (DeadLetter) message;
            log.error("Received a dead message: {}", deadLetter.message());
        }
    }
}


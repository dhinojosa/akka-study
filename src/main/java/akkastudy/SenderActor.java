package akkastudy;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class SenderActor extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) {
        log.info("Sender Actor says it got a message: {}", message);
    }
}
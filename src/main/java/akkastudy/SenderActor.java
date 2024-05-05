package akkastudy;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class SenderActor extends AbstractActor {
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchAny(message ->
                        log.info("Sender Actor says it got message: {}", message))
                .build();
    }
}
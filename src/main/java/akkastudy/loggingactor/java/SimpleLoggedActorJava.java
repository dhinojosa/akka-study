package akkastudy.loggingactor.java;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.Option;

public class SimpleLoggedActorJava extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void preStart() {
        log.debug("Starting SimpleLoggedActorJava");
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) {
        log.error(reason, "Restarting due to [{}] when processing [{}]",
                reason.getMessage(), message.isDefined() ? message.get() : "");
    }

    public void onReceive(Object message) {
        if (message instanceof String)
            log.info("Received String message in SimpleActorJava {}", message);
        else
            unhandled(message);
    }
}

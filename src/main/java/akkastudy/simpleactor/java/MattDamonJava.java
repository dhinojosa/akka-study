package akkastudy.simpleactor.java;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class MattDamonJava extends UntypedActor {
    LoggingAdapter log =
            Logging.getLogger(getContext().system(), this);

    public void onReceive(Object message) {
        if (message instanceof String) {
            System.out.println(Thread.currentThread().getName());
            log.info("Matt: Got message {}", message);
            sender().tell(1, self());
        } else unhandled(message);
    }
}

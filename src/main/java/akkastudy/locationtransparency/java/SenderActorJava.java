package akkastudy.locationtransparency.java;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class SenderActorJava extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public void onReceive(Object message) {
        if (message instanceof String) {
            log.info("Received String message in SenderActorJava: {}", message);
            log.info("Creating SimpleActorJava");
            ActorRef simpleActorJava = getContext().system().actorOf(Props.create(SimpleActorJava.class), "simpleActorJava");
            simpleActorJava.tell(message, self());
        } else {
            unhandled(message);
        }
    }
}

package akkastudy.deathwatch.java;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class DeathWatchActor extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    final ActorRef child = this.getContext().actorOf(Props.empty(), "child");

    {
        this.getContext().watch(child);
    }

    ActorRef lastSender = getContext().system().deadLetters();

    @Override
    public void onReceive(Object message) {
        if (message.equals("kill")) {
            log.info("Received message to kill");
            getContext().stop(child);
            lastSender = getSender();
        } else if (message instanceof Terminated) {
            log.info("Received Terminated Object");
            final Terminated t = (Terminated) message;
            if (t.getActor() == child) {
                lastSender.tell("finished", getSelf());
            }
        } else {
            unhandled(message);
        }
    }
}


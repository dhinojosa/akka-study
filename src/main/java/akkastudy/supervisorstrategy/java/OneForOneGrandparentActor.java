package akkastudy.supervisorstrategy.java;

import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class OneForOneGrandparentActor extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new OneForOneStrategy(10, Duration.create(1, TimeUnit.MINUTES),
                new Function<Throwable, SupervisorStrategy.Directive>() {
                    @Override
                    public SupervisorStrategy.Directive apply(Throwable param)
                             throws Exception {
                        if (param instanceof IllegalArgumentException)
                            return SupervisorStrategy.restart();
                        if (param instanceof ArithmeticException)
                            return SupervisorStrategy.resume();
                        if (param instanceof NullPointerException)
                            return SupervisorStrategy.stop();
                        else return SupervisorStrategy.escalate();
                    }
                }
        );
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Props) {
            Props props = (Props) message;
            getSender().tell(getContext().actorOf(props), self());
        } else {
            log.info("Unhandled message of type: " + message.getClass().getName());
            unhandled(message);
        }
    }
}

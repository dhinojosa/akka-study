package akkastudy.supervisorstrategy.java;

import akka.actor.*;
import akka.japi.Function;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

import static akka.actor.SupervisorStrategy.resume;
import static akka.actor.SupervisorStrategy.stop;
import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.escalate;

public class OneForOneGrandparentActor extends UntypedActor {
    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new OneForOneStrategy(10, Duration.create(1, TimeUnit.HOURS),
                new Function<Throwable, SupervisorStrategy.Directive>() {
                    @Override
                    public SupervisorStrategy.Directive apply(Throwable param) throws Exception {
                        if (param instanceof IllegalArgumentException) return stop();
                        if (param instanceof ArithmeticException) return resume();
                        if (param instanceof NullPointerException) return restart();
                        else return escalate();
                    }
                }
        );
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Props) {
            Props props = (Props) message;
            getSender().tell(getContext().actorOf(props), self());
        }
    }
}

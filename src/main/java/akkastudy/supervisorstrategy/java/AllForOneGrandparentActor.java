package akkastudy.supervisorstrategy.java;

import akka.actor.AllForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import akka.japi.Function;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class AllForOneGrandparentActor extends UntypedActor {

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new AllForOneStrategy(10, Duration.create(1, TimeUnit.HOURS),
                new Function<Throwable, SupervisorStrategy.Directive>() {
                    @Override
                    public SupervisorStrategy.Directive apply(Throwable param) throws Exception {
                        if (param instanceof IllegalArgumentException) return SupervisorStrategy.stop();
                        if (param instanceof ArithmeticException) return SupervisorStrategy.resume();
                        if (param instanceof NullPointerException) return SupervisorStrategy.restart();
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
        } else unhandled(message);
    }
}

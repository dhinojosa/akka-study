package akkastudy.supervisorstrategy.java;

import akka.actor.AllForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy.Directive;
import akka.actor.UntypedActor;
import akka.japi.Function;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

import static akka.actor.SupervisorStrategy.escalate;
import static akka.actor.SupervisorStrategy.stop;

public class AllForOneParentActor extends UntypedActor {

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new AllForOneStrategy(10, Duration.create(1, TimeUnit.HOURS),
          new Function<Throwable, Directive>() {
              @Override
              public Directive apply(Throwable param) throws Exception {
                  if (param instanceof IllegalArgumentException) return escalate();
                  if (param instanceof ArithmeticException) return escalate();
                  if (param instanceof NullPointerException) return escalate();
                  else return stop();
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
        unhandled(message);
    }
}

package akkastudy.supervisorstrategy.java;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ExceptionalChildActor extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Exception {
        if (message.equals("ArithmeticException")) throw new ArithmeticException("Oh no");
        else if (message.equals("NullPointerException")) throw new NullPointerException("Oh no");
        else if (message.equals("IllegalArgumentException")) throw new IllegalArgumentException("Oh no");
        else if (message.equals("Exception")) throw new Exception("Oh no");
        else sender().tell(String.format("Message Received %s", message), null);
    }


    @Override
    public void postStop() throws Exception {
        log.info("Exceptional Child Actor Stopped");
        super.postStop();
    }

    public void postRestart(Throwable reason) throws Exception {
        log.info("Exceptional Child Actor Restarted");
        super.postRestart(reason);
    }
}

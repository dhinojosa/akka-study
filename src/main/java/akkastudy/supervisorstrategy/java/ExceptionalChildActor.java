package akkastudy.supervisorstrategy.java;

import akka.actor.UntypedActor;

public class ExceptionalChildActor extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if (message.equals("ArithmeticException")) throw new ArithmeticException("Oh no");
        else if (message.equals("NullPointerException")) throw new NullPointerException("Oh no");
        else if (message.equals("IllegalArgumentException")) throw new IllegalArgumentException("Oh no");
        else if (message.equals("Exception")) throw new Exception("Oh no");
        else sender().tell(String.format("Message Received %s", message), null);
    }
}

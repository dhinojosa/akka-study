package akkastudy.askactor.java;

import akka.actor.AbstractActor;
import akka.actor.UntypedActor;
import akka.japi.pf.FI;

/**
 * Created by Daniel Hinojosa
 * User: Daniel Hinojosa
 * Date: 3/27/13
 * Time: 10:25 PM
 * url: <a href="http://www.evolutionnext.com">http://www.evolutionnext.com</a>
 * email: <a href="mailto:dhinojosa@evolutionnext.com">dhinojosa@evolutionnext.com</a>
 * tel: 505.363.5832
 */
public class AskActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder().matchEquals("Ping", new FI.UnitApply<String>() {
            @Override
            public void apply(String s) throws Exception {
                Thread.sleep(2000);
                System.out.println("3. " + Thread.currentThread().getName());
                sender().tell("Pong", self());
            }
        }).build();
    }
}

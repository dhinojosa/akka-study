package akkastudy.askactor.java;

import akka.actor.UntypedActor;

/**
 * Created by Daniel Hinojosa
 * User: Daniel Hinojosa
 * Date: 3/27/13
 * Time: 10:25 PM
 * url: <a href="http://www.evolutionnext.com">http://www.evolutionnext.com</a>
 * email: <a href="mailto:dhinojosa@evolutionnext.com">dhinojosa@evolutionnext.com</a>
 * tel: 505.363.5832
 */
public class AskActor extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Exception {
        if (message.equals("Ping")) {
            Thread.sleep(2000);
            System.out.println("3. " + Thread.currentThread().getName());
            sender().tell("Pong", self());
        }
    }
}

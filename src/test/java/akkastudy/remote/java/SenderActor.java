package akkastudy.remote.java;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

/**
 * @author Daniel Hinojosa
 * @since 3/24/14 2:47 PM
 * url: <a href="http://www.evolutionnext.com">http://www.evolutionnext.com</a>
 * email: <a href="mailto:dhinojosa@evolutionnext.com">dhinojosa@evolutionnext.com</a>
 * tel: 505.363.5832
 */
public class SenderActor extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Exception {
        System.out.println(String.format("Received Message Back: %s", message));

        if (message instanceof ActorRef) {
            ActorRef actorRef = (ActorRef) message;
            actorRef.tell("Message from source", getSelf());
        }
    }
}

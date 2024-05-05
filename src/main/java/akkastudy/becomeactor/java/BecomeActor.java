package akkastudy.becomeactor.java;

import akka.actor.AbstractActor;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;
import akka.japi.pf.FI;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

/**
 * @author Daniel Hinojosa
 * @evolutionnext.com</a> tel: 505.363.5832
 * @since 3/19/14 12:10 PM
 * url: <a href="http://www.evolutionnext.com">http://www.evolutionnext.com</a>
 * email: <a href="mailto:dhinojosa@evolutionnext.com">dhinojosa
 */
public class BecomeActor extends AbstractActor {
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private Receive inRealBigTroubleReceive = receiveBuilder()
            .matchAny(o -> log.info("Become Actor (In Real Big Trouble) " +
                    "received message: {}", o))
            .matchEquals("spendTimeInPrison", o -> getContext().unbecome())
            .matchAny(o -> getSender().tell("No", getSelf())).build();


    private Receive inTrouble = receiveBuilder()
            .matchAny(
                    o -> log.info("Become Actor (In Trouble) received " +
                            "message: {}", o))
            .matchEquals("askForgiveness", s -> getContext().unbecome())
            .matchEquals("buyFlowers", s -> getContext().unbecome())
            .matchEquals("getDrunkAndCrashCar", s -> getContext().become
                    (inRealBigTroubleReceive))
            .matchAny(o -> getSender().tell("No", getSelf())).build();

    public void onReceive(Object message) {
        log.info("Become Actor received message: {}", message);
        if (message.equals("goHiking"))
            getSender().tell("Good hike!", getSelf());
        if (message.equals("goOutToDinner")) getSender().tell("Nom", getSelf());
        if (message.equals("getDrunkAndCrashCar"))
            getContext().become(inRealBigTroubleReceive);
        if (message.equals("useFoulLanguage")) getContext().become(inTrouble);
        if (message.equals("leaveWithoutSayingThankYou"))
            getContext().become(inTrouble);
        unhandled(message);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("goHiking",
                        s -> getSender().tell("Good hike", getSelf()))
                .matchEquals("goOutToDinner",
                        s -> getSender().tell("Nom", getSelf()))
                .matchEquals("getDrunkAndCrashCar",
                        s -> getContext().become(inRealBigTroubleReceive))
                .matchEquals("useFoulLanguage",
                        s -> getContext().become(inTrouble))
                .build();
    }
}

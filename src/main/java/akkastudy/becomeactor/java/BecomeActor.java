package akkastudy.becomeactor.java;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;

/**
 * @author Daniel Hinojosa
 * @since 3/19/14 12:10 PM
 * url: <a href="http://www.evolutionnext.com">http://www.evolutionnext.com</a>
 * email: <a href="mailto:dhinojosa@evolutionnext.com">dhinojosa@evolutionnext.com</a>
 * tel: 505.363.5832
 */
public class BecomeActor extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    Procedure<Object> inRealBigTrouble = new Procedure<Object>(){
        @Override
        public void apply(Object message) throws Exception {
            log.info("Become Actor (In Real Big Trouble) received message: {}", message);
            if (message.equals("spendTimeInPrison")) getContext().unbecome();
            else getSender().tell("No", getSelf());
        }
    };

    Procedure<Object> inTrouble = new Procedure<Object>(){
        @Override
        public void apply(Object message) throws Exception {
            log.info("Become Actor (In Trouble) received message: {}", message);
            if (message.equals("askForgiveness")) getContext().unbecome();
            if (message.equals("buyFlowers")) getContext().unbecome();
            if (message.equals("getDrunkAndCrashCar")) getContext().become(inRealBigTrouble);
            else getSender().tell("No", getSelf());
        }
    };

    @Override
    public void onReceive(Object message) throws Exception {
        log.info("Become Actor received message: {}", message);
        if (message.equals("goHiking")) getSender().tell("Good hike!", getSelf());
        if (message.equals("goOutToDinner")) getSender().tell("Nom", getSelf());
        if (message.equals("getDrunkAndCrashCar")) getContext().become(inRealBigTrouble);
        if (message.equals("useFoulLanguage")) getContext().become(inTrouble);
        if (message.equals("leaveWithoutSayingThankYou")) getContext().become(inTrouble);
        unhandled(message);
    }
}

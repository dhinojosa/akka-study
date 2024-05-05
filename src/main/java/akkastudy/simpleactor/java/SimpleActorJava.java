package akkastudy.simpleactor.java;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.FI;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SimpleActorJava extends AbstractActor {
    private LoggingAdapter log =
            Logging.getLogger(getContext().system(),
                    this);

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(String.class, message -> {
            System.out.println(Thread.currentThread().getName());
            log.info("Received String message in Simple Actor Java {}",
                    message);
        }).build();
    }

    public static void main(String[] args) throws TimeoutException,
            InterruptedException {
        ActorSystem actorSystem = ActorSystem.create("My-Actor-System");
        Props props = Props.create(SimpleActorJava.class);
        ActorRef actorRef = actorSystem.actorOf(props);
        actorRef.tell("Hello", ActorRef.noSender());


        Await.ready(actorSystem.terminate(), Duration.create(10, TimeUnit
                .SECONDS));
    }
}

package akkastudy.eventbus.scala

import akka.event.{ActorClassification, ActorClassifier, ActorEventBus}
import akka.actor.ActorRef

/**
 * ActorEventBus is a trait that sets a Subscriber as an ActorRef, with a comparison of subscribers
 * ActorClassifier will create an ActorRef as the Classifier
 * @author Daniel Hinojosa
 * @since 4/9/14 9:40 PM
 *        url: <a href="http://www.evolutionnext.com">http://www.evolutionnext.com</a>
 *        email: <a href="mailto:dhinojosa@evolutionnext.com">dhinojosa@evolutionnext.com</a>
 *        tel: 505.363.5832
 */


class SimpleActorEventBus extends ActorEventBus with ActorClassifier with ActorClassification {
  type Event = SimpleActorRefEvent

  /* How many actor refs do you expect to be handled */
  override protected def mapSize: Int = 33

  /* Classify the events being sent and return an Actor Ref (which is the classifier type) */
  override protected def classify(event: Event): Classifier = event.to
}

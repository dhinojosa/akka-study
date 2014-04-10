package akkastudy.eventbus.scala

import akka.event.{SubchannelClassification, EventBus}
import akka.util.Subclassification

/**
 *
 * @author Daniel Hinojosa
 * @since 3/28/14 7:00 PM
 *        url: <a href="http://www.evolutionnext.com">http://www.evolutionnext.com</a>
 *        email: <a href="mailto:dhinojosa@evolutionnext.com">dhinojosa@evolutionnext.com</a>
 *        tel: 505.363.5832
 */

class FooSubclassification extends Subclassification[String] {
  override def isEqual(x: String, y: String): Boolean =
    x == y

  override def isSubclass(x: String, y: String): Boolean =
    x.startsWith(y)
}

class SubchannelClassificationEventBus extends EventBus with SubchannelClassification {
  override type Subscriber = SimpleSubscriber
  override type Classifier = String
  override type Event = SimpleEvent

  override protected val subclassification: Subclassification[Classifier] = new FooSubclassification

  // is used for extracting the classifier from the incoming events
  override protected def classify(event: Event): Classifier = event.category

  // will be invoked for each event for all subscribers which registered
  // themselves for the event’s classifier
  override protected def publish(event: Event, subscriber: Subscriber): Unit = {
    subscriber.process(event.message)
  }
}

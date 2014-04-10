package akkastudy.eventbus.scala

import akka.event.{EventBus, ScanningClassification}

/**
 *
 * ScanningClassificationEventBus allows the user to select how it matches messages.  This can potentially
 * process more that one depending on the implementation of matches
 *
 * @author Daniel Hinojosa
 * @since 4/9/14 9:03 PM
 *        url: <a href="http://www.evolutionnext.com">http://www.evolutionnext.com</a>
 *        email: <a href="mailto:dhinojosa@evolutionnext.com">dhinojosa@evolutionnext.com</a>
 *        tel: 505.363.5832
 */
class ScanningClassificationEventBus extends EventBus with ScanningClassification {
  type Event = SimpleEvent
  type Classifier = String
  type Subscriber = SimpleSubscriber

  // is needed for determining matching classifiers and storing them in an
  // ordered collection
  override protected def compareClassifiers(a: Classifier, b: Classifier): Int = a compareTo b

  // is needed for storing subscribers in an ordered collection
  override protected def compareSubscribers(a: Subscriber, b: Subscriber): Int = a compareTo b

  // determines whether a given classifier shall match a given event; it is invoked
  // for each subscription for all received events, hence the name of the classifier
  override protected def matches(classifier: Classifier, event: Event): Boolean =
    event.category endsWith classifier

  // will be invoked for each event for all subscribers which registered themselves
  // for a classifier matching this event
  override protected def publish(event: Event, subscriber: Subscriber): Unit = {
    subscriber process event.message
  }
}

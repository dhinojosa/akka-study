package akkastudy.eventbus.scala

import akka.event.{EventBus, LookupClassification}

/**
 *
 * @author Daniel Hinojosa
 * @since 3/28/14 7:00 PM
 *        url: <a href="http://www.evolutionnext.com">http://www.evolutionnext.com</a>
 *        email: <a href="mailto:dhinojosa@evolutionnext.com">dhinojosa@evolutionnext.com</a>
 *        tel: 505.363.5832
 */

class LookupClassificationEventBus extends EventBus with LookupClassification {

  implicit object SimpleSubscriberOrdering extends Ordering[SimpleSubscriber] {
    override def compare(x: SimpleSubscriber, y: SimpleSubscriber): Int = {
      val diff = x.messages.size - y.messages.size
      diff match {
        case 0 =>
          val zipped = x.messages.zip(y.messages).filter{case (a, b) => a != b}
          zipped.size match {
            case 0 => 0
            case _ => zipped(0)._1.compareTo(zipped(0)._2)
          }
        case _ => diff
      }
    }
  }

  override type Subscriber = SimpleSubscriber
  override type Classifier = String
  override type Event = SimpleEvent

  override protected def publish(event: Event, subscriber: Subscriber): Unit = subscriber.process(s"${event.message} ${event.time}")

  override protected def classify(event: Event): Classifier = event.category

  override protected def compareSubscribers(a: Subscriber, b: Subscriber): Int = implicitly[Ordering[SimpleSubscriber]].compare(a, b)

  override protected def mapSize(): Int = 41
}

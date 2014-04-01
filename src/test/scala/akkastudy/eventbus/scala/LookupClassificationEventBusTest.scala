package akkastudy.eventbus.scala

import org.scalatest.{FlatSpec, Matchers}

/**
 *
 * @author Daniel Hinojosa
 * @since 4/1/14 1:59 PM
 *        url: <a href="http://www.evolutionnext.com">http://www.evolutionnext.com</a>
 *        email: <a href="mailto:dhinojosa@evolutionnext.com">dhinojosa@evolutionnext.com</a>
 *        tel: 505.363.5832
 */
class LookupClassificationEventBusTest extends FlatSpec with Matchers {
  behavior of "A simple event bus that extends EventBus"

  it should
    """given some subscribers it should handle some basic events,
      | if we just create an EventBus with no ties to an ActorSystem """ in {
    val lookupClassificationEventBus = new LookupClassificationEventBus()
    val mySubscriber = new SimpleSubscriber()
    lookupClassificationEventBus.subscribe(mySubscriber, "Category-A")
    lookupClassificationEventBus.publish(SimpleEvent("Category-A", "Sending Category-A Message!", "2014-03-28 12:00:00"))
    lookupClassificationEventBus.publish(SimpleEvent("Category-B", "Sending Category-B Message!", "2014-03-28 12:01:00"))
    lookupClassificationEventBus.publish(SimpleEvent("Category-B", "Sending Category-B Message!", "2014-03-28 12:02:00"))
    mySubscriber.messages should be (List("Sending Category-A Message! 2014-03-28 12:00:00"))
  }
}

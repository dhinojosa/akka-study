package akkastudy.eventbus.scala

import org.scalatest.{Matchers, FlatSpec}
import scala.language.reflectiveCalls
/**
 *
 * @author Daniel Hinojosa
 * @since 3/28/14 3:59 PM
 *        url: <a href="http://www.evolutionnext.com">http://www.evolutionnext.com</a>
 *        email: <a href="mailto:dhinojosa@evolutionnext.com">dhinojosa@evolutionnext.com</a>
 *        tel: 505.363.5832
 */
class SimpleEventBusTest extends FlatSpec with Matchers {
  behavior of "A simple event bus that extends EventBus"

  it should
    """given some subscribers it should handle some basic events,
      | if we just create an EventBus with no ties to an ActorSystem """ in {
      val simpleEventBus = new SimpleEventBus()
      val mySubscriber = new SimpleSubscriber()
      simpleEventBus.subscribe(mySubscriber, "Objects")
      simpleEventBus.publish(SimpleEvent("Basic", "Sending Message!", "2014-03-28 12:00:00"))
      mySubscriber.messages should be (List("Sending Message! 2014-03-28 12:00:00"))
  }
}

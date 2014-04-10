package akkastudy.eventbus.scala

import org.scalatest.{Matchers, FlatSpec}

/**
 *
 * @author Daniel Hinojosa
 * @since 4/9/14 8:40 PM
 *        url: <a href="http://www.evolutionnext.com">http://www.evolutionnext.com</a>
 *        email: <a href="mailto:dhinojosa@evolutionnext.com">dhinojosa@evolutionnext.com</a>
 *        tel: 505.363.5832
 */
class SubchannelClassificationEventBusTest extends FlatSpec with Matchers {
  behavior of "A SubchannelClassificationEventBus"

  it should "accept only message match the subclassification if the subclassification matches" in {
    val subchannelClassificationEventBus = new SubchannelClassificationEventBus
    val simpleSubscriber = new SimpleSubscriber()
    subchannelClassificationEventBus.subscribe(simpleSubscriber, "abc")
    subchannelClassificationEventBus.publish(SimpleEvent("abcdef", "Sending abcdef Message!", "2014-03-28 12:00:00"))
    subchannelClassificationEventBus.publish(SimpleEvent("defabc", "Sending defabc Message!", "2014-03-28 12:01:00"))
    subchannelClassificationEventBus.publish(SimpleEvent("jklmno", "Sending jklmno Message!", "2014-03-28 12:02:00"))
    simpleSubscriber.messages.size should be (1)
  }

  it should "accept only message match the subclassification if the subclassification matches with 2 matches" in {
    val subchannelClassificationEventBus = new SubchannelClassificationEventBus
    val simpleSubscriber = new SimpleSubscriber()
    subchannelClassificationEventBus.subscribe(simpleSubscriber, "abc")
    subchannelClassificationEventBus.publish(SimpleEvent("abcdef", "Sending abcdef Message!", "2014-03-28 12:00:00"))
    subchannelClassificationEventBus.publish(SimpleEvent("defabc", "Sending defabc Message!", "2014-03-28 12:01:00"))
    subchannelClassificationEventBus.publish(SimpleEvent("jklmno", "Sending jklmno Message!", "2014-03-28 12:02:00"))
    subchannelClassificationEventBus.publish(SimpleEvent("abcjkl", "Sending abcdef Message!", "2014-03-28 12:00:00"))
    simpleSubscriber.messages.size should be (2)
  }
}

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
class ScanningClassificationEventBusTest extends FlatSpec with Matchers {
   behavior of "A ScanningClassificationEventBus"

   it should "accept only message match the matching the classification" in {
     val scanningClassificationEventBus = new ScanningClassificationEventBus
     val simpleSubscriber = new SimpleSubscriber()
     scanningClassificationEventBus.subscribe(simpleSubscriber, "abc")
     scanningClassificationEventBus.publish(SimpleEvent("dpsdef", "Sending abcdef Message!", "2014-03-28 12:00:00"))
     scanningClassificationEventBus.publish(SimpleEvent("defabc", "Sending defabc Message!", "2014-03-28 12:01:00"))
     scanningClassificationEventBus.publish(SimpleEvent("jklmno", "Sending jklmno Message!", "2014-03-28 12:02:00"))
     simpleSubscriber.messages.size should be (1)
   }

   it should "accept only message match the subclassification if the subclassification matches with 2 matches" in {
     val scanningClassificationEventBus = new ScanningClassificationEventBus
     val simpleSubscriber = new SimpleSubscriber()
     scanningClassificationEventBus.subscribe(simpleSubscriber, "abc")
     scanningClassificationEventBus.publish(SimpleEvent("abcdef", "Sending abcdef Message!", "2014-03-28 12:00:00"))
     scanningClassificationEventBus.publish(SimpleEvent("defabc", "Sending defabc Message!", "2014-03-28 12:01:00"))
     scanningClassificationEventBus.publish(SimpleEvent("jklmno", "Sending jklmno Message!", "2014-03-28 12:02:00"))
     scanningClassificationEventBus.publish(SimpleEvent("abcjkl", "Sending abcdef Message!", "2014-03-28 12:00:00"))
     simpleSubscriber.messages.size should be (1)
   }
 }

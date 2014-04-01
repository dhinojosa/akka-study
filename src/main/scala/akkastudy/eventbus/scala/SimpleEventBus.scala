package akkastudy.eventbus.scala

import akka.event.EventBus
import scala.language.reflectiveCalls



class SimpleEventBus extends EventBus{
  private var subscribers = List[Subscriber]()

  override def publish(event: Event): Unit = {
    subscribers.foreach(_.process(event.message + " " + event.time))
  }

  override def unsubscribe(subscriber: Subscriber): Unit = {
    subscribers = subscribers.filterNot(_ == subscriber)
  }

  override def unsubscribe(subscriber: Subscriber, from: Classifier): Boolean = {
    unsubscribe(subscriber)
    true
  }

  override def subscribe(subscriber: Subscriber, to: Classifier): Boolean = {
    subscribers = subscribers :+ subscriber
    true
  }

  override type Subscriber = {def process(value:String):Unit}
  override type Classifier = String
  override type Event = SimpleEvent
}

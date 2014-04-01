package akkastudy.eventbus.scala

/**
 *
 * @author Daniel Hinojosa
 * @since 3/28/14 11:38 PM
 *        url: <a href="http://www.evolutionnext.com">http://www.evolutionnext.com</a>
 *        email: <a href="mailto:dhinojosa@evolutionnext.com">dhinojosa@evolutionnext.com</a>
 *        tel: 505.363.5832
 */
class SimpleSubscriber {
  var _messages = List[String]()

  def messages = _messages

  def process(s:String):Unit = {
    _messages = _messages :+ s
  }
}

package akkastudy.eventbus.scala

/**
 *
 * @author Daniel Hinojosa
 * @since 3/28/14 11:38 PM
 *        url: <a href="http://www.evolutionnext.com">http://www.evolutionnext.com</a>
 *        email: <a href="mailto:dhinojosa@evolutionnext.com">dhinojosa@evolutionnext.com</a>
 *        tel: 505.363.5832
 */
class SimpleSubscriber extends Comparable[SimpleSubscriber] {
  var _messages = List[String]()

  def messages = _messages

  def process(s:String):Unit = {
    _messages = _messages :+ s
  }

  override def compareTo(o: SimpleSubscriber): Int = {
    val result = this._messages.size - o._messages.size
    if (result == 0) {
      this._messages.zip(o._messages).map{case (x,y) => x.compareTo(y)}.sum
    } else result
  }
}

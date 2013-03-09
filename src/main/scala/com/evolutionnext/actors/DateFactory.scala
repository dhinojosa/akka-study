package com.evolutionnext.actors

import java.util.Date

trait DateFactory {
  def create:Date
}

trait RealDateFactory extends DateFactory {
    def create:Date = new Date()
}

trait FakeDateFactory extends DateFactory {
    def create:Date = new java.util.GregorianCalendar(2012, 2, 13, 12, 10, 10).getTime
}

class Article { self: DateFactory =>
  private var _publishDate:Option[Date] = None
  def publish_!() {
    self._publishDate = Some(self.create)
  }
  def publishDate = _publishDate.get
}

object DateTester extends App {
  val article = new Article with RealDateFactory
  val article2 = new Article with FakeDateFactory
  article.publish_!()
  article2.publish_!()
  println(article.publishDate)
  println(article2.publishDate)
  type DefaultArticle = Article with RealDateFactory
}




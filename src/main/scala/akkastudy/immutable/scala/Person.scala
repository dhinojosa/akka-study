package akkastudy.immutable.scala

class Person(val firstName: String, val lastName: String) {self =>
  override def equals(other: Any):Boolean = {
    other match {
      case x: Person =>
        val otherPerson = x.asInstanceOf[Person]
        otherPerson.firstName == self.firstName &&
          otherPerson.lastName == self.lastName
      case _ => false
    }
  }

  override def hashCode:Int = {
    59 + self.firstName.hashCode + self.lastName.hashCode
  }

  override def toString:String = "Person(%s, %s)".format(firstName, lastName)
}

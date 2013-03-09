package akkastudy.typedactor.scala

case class Person(firstName: String, lastName: String)

trait RegistrationActor {
  def registerPerson(person: Person)

  def getCount: Int
}

class RegistrationActorImpl extends RegistrationActor {
  var list = List[Person]()

  def registerPerson(person: Person) {
    list = list :+ person
  }

  def getCount: Int = list.size
}


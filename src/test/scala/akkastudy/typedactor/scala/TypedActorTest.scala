package akkastudy.typedactor.scala

import org.scalatest.FlatSpec
import akka.actor.{TypedActor, TypedProps, ActorSystem}

class TypedActorTest extends FlatSpec {
  behavior of "A simple typed actor"

  it should "register a person, and then determine the size asychronously" in {
    val system = ActorSystem("MySystem")
    val registrationActor =
      TypedActor(system).typedActorOf(TypedProps[RegistrationActorImpl], name = "registrationActor")
    registrationActor.registerPerson(Person("Cesar", "Chavez"))
  }
}
package toppackage

import cats.implicits._


//Covariant Functor
abstract class CovariantType[+A]() { self =>

  def someProducerMethod:A

  // home grown "map" method to convert CovariantType[A] to CovariantType[B]
  def changeAtoB[B](f:A=>B): CovariantType[B] =  new CovariantType[B] {

    override def someProducerMethod: B = f(self.someProducerMethod)

  }

}

//Contravariant Functor
abstract class SweetnessChecker[-A]() { self=>

  def isSweet(a: A): Boolean

  def contramap[Z](f:Z=>A) = new SweetnessChecker[Z] {

    override def isSweet(z: Z): Boolean = self.isSweet(f(z))

  }

}

// A custom container
class Container[+A](val item: A)

// Functor based functions
object WorkWithMappable {

  implicit val functorInstanceForContainer = new cats.Functor[Container] {

    override def map[A, B](fa: Container[A])(f: A => B) = new Container[B](f(fa.item))

  }

  //process any Functor. F[_] indicates any generic type having single type parameter...one hole
  def changeContainerContentsForAnyMappable[F[_], A](container:F[A])(implicit functor:cats.Functor[F])  =

    container.map(x=> "You got a Generic 'Box' with a String in it")


  changeContainerContentsForAnyMappable(Option("hello"))

  changeContainerContentsForAnyMappable(List(1,2,3))

  changeContainerContentsForAnyMappable(new Container("hello"))

}



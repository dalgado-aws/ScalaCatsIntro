//Invariant Functor
package toppackage

abstract class DollarConverterFor[A] { self =>

  //Consumer Method consumes A
  def toDollar(v:A):Double

  //Producer Method produces A
  def fromDollar(y:Double):A

  def imap[B](f:A=>B)(g:B=>A):DollarConverterFor[B] = new DollarConverterFor[B] {

    // Use B=>A and invoke the toDollar() method from Dollar[A]
    override def toDollar(v: B): Double = self.toDollar(g(v))
    // same as contramap

    // Use A=>B to convert the result of the method from Dollar[A]
    override def fromDollar(y: Double): B =  f(self.fromDollar(y))
    // same as map

  }
}

case class Euro (euroValue:Double)

case class Yen (yenValue:Double)

case class Pound(poundValue:Double)

object InvariantFunctorExamples {
  val euroToPound = (e:Euro) => Pound(e.euroValue/2)

  val poundToEuro = (p:Pound) => Euro(p.poundValue *2)

  val euroAndDollar =  new DollarConverterFor[Euro] () {

    override def toDollar(v: Euro): Double = v.euroValue/0.75

    override def fromDollar(y: Double): Euro = new Euro(y * 0.75)

  }

  val euroAndPound = euroAndDollar.imap(euroToPound)(poundToEuro)

  euroAndPound.toDollar(Pound(33))

  val anotherEuroAndDollar = euroAndPound.imap(poundToEuro)(euroToPound)

  euroAndDollar.toDollar(Euro(33))
}

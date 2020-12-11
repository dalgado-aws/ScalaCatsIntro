package toppackage

import cats._
import cats.implicits._

object  MonoidSamples {

  1 |+| 2 // Using Monoid Interface Syntax

  Monoid[Int].combine(1,2) // Using Monoid Interface Definition

  Monoid.combine(1,2) // Using Monoid Interface Definition

  2 |+|  Monoid[Int].empty // Using Monoid Identity as second param

  // Cats provides instances for many of the Scala Standard Library Classes
  Option(2) |+| Option(3) |+| Option(6)

  Map(1->2) |+| Map(2->3) |+| Map.empty

  List(1,2,3) |+| List(4,5,6) |+| List(7)

  val listOfMaps = List(Map(1->1, 2->2), Map(3->3, 4->4))

  listOfMaps.reduce( _ |+| _)

  // A function to process any monoid
  def processMonoid[A](a:A)(implicit m:Monoid[A]): A = m.combine(a, m.empty)

  // Invoking the function with various Monoids
  processMonoid(List(2,3, 4))

  processMonoid(1)

  processMonoid("hello")

}

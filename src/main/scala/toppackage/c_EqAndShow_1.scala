package toppackage

import cats._
import cats.implicits._

object CatsEqualsAndShow extends App {

  def testCatsShow(): Unit = {

    "hello World".show

    1.show
  }

  def testCatsEquals(): Unit = {

    Eq[Int].eqv(1,2)

    1.eqv(2)

    1234 === 1234

    //erroneously comparing 1 with the letter "L". No error detected
    1 == "l"

    //compile time error
    //Eq[Int].eqv(1, "l")
  }
}

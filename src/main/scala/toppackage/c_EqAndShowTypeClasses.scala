package toppackage

import cats._
import cats.implicits._




object CatsEqualsAndShow {

  def testCatsShow(): Unit = {

    "hello World".show

    1.show
  }

  def testCatsEquals(): Unit = {

    Eq[Int].eqv(1,2)

    1.eqv(2)

    1234 === 1234
  }
}


// Using Eq Type Class with our custom class PlainOrder
object CatsEqualsForCustomClassPlainOrder {

  val order1 = new PlainOrder(1, 2, 3)
  val order2 = new PlainOrder(1, 2, 3)

  // Need Instance definition for PlainOrder to use Eq with PlainOrder
  implicit val compareOrders:cats.Eq[PlainOrder] = (a: PlainOrder, b: PlainOrder) => a.amount > b.amount

  // Instance definition will be used in comparison
  def testCustomInstance(): Unit = {

    // Using Interface Definition
    Eq[PlainOrder].eqv(order1, order2)

    // Using Interface Syntax
    order1 === order2

    order1 == "hello" // no compile error using Scala's == operator

    //order1 === "hello" //compile error with Eq Type Class
  }

  // To use Eq for Option[Order], Eq[Option[Order]] is required. There are 2 ways:
  //1. Define val eqOptionPlainOrderInstance = new Eq[Option[PlainOrder]] ...
  //2. Define a function to convert Eq[PlainOrder] to Eq[Option[PlainOrder]]
  // Below is a dummy implementation of the second approach
  implicit def convert(plainOrderComparer:Eq[PlainOrder]):Eq[Option[PlainOrder]] = new Eq[Option[PlainOrder]] {

    override def eqv(x: Option[PlainOrder], y: Option[PlainOrder]): Boolean = {

      val optionCompares = for (po1 <- x; po2 <- y)  yield plainOrderComparer.eqv(po1, po2)

      optionCompares.getOrElse(false)
    }
  }

  def testOptionInstance(): Unit = {

    val orderOption1 = Option(order1)

    val orderOption2 = Option(order2)

    Eq[Option[PlainOrder]].eqv(Some(order1), Some(order2))

    orderOption1 === orderOption2
  }

  // We could also write a generic converter which converts Eq[SomeCustomType] to Eq[_[SomeCustomType]]
  // where _ stands for any Generic ...List, Set etc
  // See https://www.scalawithcats.com/dist/scala-with-cats.html

  def processPlainOrdersHavingShow[A<:PlainOrder](order:A)(implicit shower:Show[A]) = f"${order.orderId} ${order.show}"
}



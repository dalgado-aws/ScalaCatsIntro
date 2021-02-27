package toppackage

import cats._
import cats.implicits._

// Using Eq Type Class with our custom class PlainOrder
object CatsEqualsForCustomClassPlainOrder extends  App {

  val order1 = new PlainOrder(1, 2, 3)
  val order2 = new PlainOrder(1, 2, 3)

  // Need Instance definition for PlainOrder to use Eq with PlainOrder
  implicit val compareOrders:cats.Eq[PlainOrder] = (a: PlainOrder, b: PlainOrder) => a.amount > b.amount

  // Instance definition will be used in comparison

  // Using Interface Definition
  Eq[PlainOrder].eqv(order1, order2)

  // Using Interface Syntax
  order1 === order2

  //order1 == "hello" // no compile error using Scala's == operator

  //order1 === "hello" //compile error with Eq Type Class

  // Cats defines function to convert Eq[PlainOrder] to Eq[Option[PlainOrder]] so we can use Eq for Option[PlainOrder]]
  // even though we have not defined Eq[Option[PlainOrder]]

  val orderOption1 = Option(order1)

  val orderOption2 = Option(order2)

  Eq[Option[PlainOrder]].eqv(Some(order1), Some(order2))

  println("Comparing Option[Order]", orderOption1 === orderOption2)

  def processPlainOrdersHavingShow[A<:PlainOrder](order:A)(implicit shower:Show[A]) = f"${order.orderId} ${order.show}"
}



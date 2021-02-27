// FP Programming Style with Type Classes
package toppackage

// Type Class definition
trait OrderSaver[A] {

  def saveOrder(value:A) :Int

}

// Type Class Instances
// Dummy implementation
object OrderSaverInstances {

  implicit val plainOrderSaver:OrderSaver[PlainOrder] = (value: PlainOrder) => {

    println("Saving PlainOrder"); Status.Success
  }

  implicit val expressOrderSaver:OrderSaver[ExpressOrder] = (value: ExpressOrder) => {

    println("Saving ExpressOrder"); Status.Success
  }

  implicit val orderBasketSaver:OrderSaver[List[PlainOrder]] = (orderBasket: List[PlainOrder]) => {

    println("Saving List[PlainOrder]"); Status.Success
  }

 implicit val marsOrderSaver:OrderSaver[UnrelatedMarsOder] = (value: UnrelatedMarsOder) => {

   println("Saving MarsOrder"); Status.Success
 }

  // Interface Definition - Connecting Instances to Types
  def saveOrder[A](a:A)(implicit orderSaverInstance:OrderSaver[A]) = orderSaverInstance.saveOrder(a)

  // Interface Syntax - Another way of connecting Instances to Types
  implicit class OrderSaverInterfaceSyntax[A](value:A) {

    def save(implicit saver:OrderSaver[A]) = saver.saveOrder(value)
  }

  // Making a Type Class instance for Int
  implicit val intOrderSaver:OrderSaver[Int] = (value:Int)=>Status.Success
}

// Testing Type Classes
object TestFunctionalOrderSaver extends App {

  import OrderSaverInstances._

  val plainOrder = new PlainOrder(1,2,3)

  OrderSaverInstances.saveOrder(plainOrder)

  plainOrder.save

  1.save
}


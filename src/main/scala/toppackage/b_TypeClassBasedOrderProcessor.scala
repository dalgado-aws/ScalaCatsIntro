// FP Programming Style with Type Classed
package toppackage

trait OrderSaver[A] {
  def saveOrder(value:A) :Int
}

// Type Class Instances
object OrderSaverInstances {

  implicit val plainOrderSaver:OrderSaver[PlainOrder] = (value: PlainOrder) => Status.Success

  implicit val expressOrderSaver:OrderSaver[ExpressOrder] = (value: ExpressOrder) => Status.Success

  implicit val orderBasketSaver:OrderSaver[List[PlainOrder]] = (orderBasket: List[PlainOrder]) => Status.Success

 implicit val marsOrderSaver:OrderSaver[UnrelatedMarsOder] = (value: UnrelatedMarsOder) => Status.Success

  // Interface Definition - Connecting Instances to Types
  def saveOrder[A](a:A)(implicit orderSaverInstance:OrderSaver[A]) = orderSaverInstance.saveOrder(a)

  // Interface Syntax - Another way of connecting Instances to Types
  implicit class OrderSaverInterfaceSyntax[A](value:A) {

    def save(implicit saver:OrderSaver[A]) = saver.saveOrder(value)

  }

  //making a Type Class instance for Int
  implicit val intOrderSaver:OrderSaver[Int] = (value:Int)=>Status.Success

}

// Testing Type Classes
object TestFunctionalOrderSaver {

  import OrderSaverInstances._

  def saveOrders (): Unit = {

    val plainOrder = new PlainOrder(1,2,3)

    OrderSaverInstances.saveOrder(plainOrder)

    plainOrder.save

    1.save
  }
}


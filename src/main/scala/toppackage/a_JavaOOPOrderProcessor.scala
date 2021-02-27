// OOP Programming Style
package toppackage

// Collecting all the functions in one class. Similar to FP, separating data and functions.
// Dummy implementation
class OOOrderProcessor {

  def processOrder(plainOrder: PlainOrder) = Status.Success

  def processOrder(expressOrder: ExpressOrder) = Status.Success

  def processOrderBasket(orderBasket: OrderBasket): List[Int] = orderBasket.orders.map(o => processOrder(o))

  def processUnrelatedMarsOrder(marsOder: UnrelatedMarsOder): Int = Status.Success
}

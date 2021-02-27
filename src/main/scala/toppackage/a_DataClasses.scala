package toppackage

object Status {

  val Success = 0

  val Failure = -1
}

class PlainOrder(val orderId:Int,
                 val itemId:Int,
                 val amount:Int)

// Specialized Order derived from PlainOrder
class ExpressOrder(orderId:Int, itemId:Int, amount:Int, expressCharges:Int)
  extends PlainOrder(orderId, itemId, amount)

// Order ContainerV2. List is Co Variant so orders can be List[PlainOrder] or List[ExpressOrder]
class OrderBasket(val orders:List[PlainOrder])

// Another Order class, but unrelated to the PlainOrder inheritance hierarchy
class UnrelatedMarsOder(info:String)
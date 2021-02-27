package toppackage

import cats.implicits._
import cats.data.Writer

object TestingWriter {

  val applyHolidayDiscount = (price:Double) => Writer(List(s"applying holiday discount 2% off ${price} => ${price*0.98}"), price*0.98)

  val applyingEmployeeDiscount = (price:Double) => Writer(List(s"applying employee discount 3% off ${price} => ${price * 0.97}"), price * 0.97 )

  val applyingMembershipDiscount = (price:Double) => Writer(List(s"applying membership discount 2% off ${price} => ${price * 0.98}"), price * 0.98 )

  val costPrice = Writer(List("Start price is 1000"), 1000d)

  val anotherStartPrice = 1000d.writer(List("Start price is 1000"))

  val discountedPrice = Writer(List("Processing start price of 1000"), 1000.0).
    flatMap(applyHolidayDiscount).
    flatMap(applyingEmployeeDiscount).
    flatMap(applyingMembershipDiscount)

  //cap of discount to 90% of start price

  val finalPrice = discountedPrice.flatMap( discountedPriceValue =>

    costPrice.flatMap(costPriceValue =>

      //discounted Price is more than 90% of Cost Price
      if(discountedPriceValue > 0.9 * costPriceValue)

        Writer(List("Capping discount to 90%"), costPriceValue*0.9)

      else

        Writer(List("Discount price approved!"), discountedPriceValue)
    ) )

  finalPrice.value

  finalPrice.written

  val(log, result) = finalPrice.run

  println("log is ", log)

  println("result is", result)
}

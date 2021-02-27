package toppackage

import cats._
import cats.data.Reader

object g_MonadFlavours_4_Reader extends App {

  val COST_PRICE = 1000;

  def federalTaxCalculator(costPrice:Int):Double = costPrice * 0.1

  def stateTaxCalculator(costPrice:Int):Double =  costPrice * 0.01

  // 1. Combining two function having same dependency without Reader
  def combineBothFunctionsWithoutReader(costPrice:Int) = {

    val stateTax = stateTaxCalculator(costPrice)

    val federalTax = federalTaxCalculator(costPrice)

    federalTax  + stateTax
  }

  println("Combine Without Reader", combineBothFunctionsWithoutReader(COST_PRICE))

  // 2. Combining two function having same dependency using Reader
  val costPriceReaderForFederalTax = Reader(federalTaxCalculator)

  val costPriceReaderForStateTax = Reader(stateTaxCalculator)

  val  combineBothFunctionsWithReader = costPriceReaderForFederalTax.flatMap(federalTax=>

    costPriceReaderForStateTax.flatMap(stateTax =>

      Reader((costPrice:Int) => {

        println(s"federalTax: ${federalTax} stateTax: ${stateTax}")

        federalTax +  stateTax;
      }
      )))

  println("Combine With Reader", combineBothFunctionsWithReader.run(COST_PRICE))

  // 3. Combining two function having same dependency with Reader using for-comprehension
  val combineBothFunctionsWithForComprehensionSyntax = for {

    federalTax <- costPriceReaderForFederalTax

    stateTax <- costPriceReaderForStateTax

  } yield federalTax + stateTax
  println("Combine With Reader using for comprehension", combineBothFunctionsWithForComprehensionSyntax(COST_PRICE))


  // Working with Readers. Processing a Reader(Similar to Option)
  val anotherReader = costPriceReaderForStateTax.flatMap(stateTax =>

    Reader((costPrice:Int) =>

      if( costPrice< 1000) stateTax else costPrice*0.1)) ;

  costPriceReaderForFederalTax(COST_PRICE)

  for(salesTax <- costPriceReaderForFederalTax) yield salesTax


  // What if we want to combine a function that takes 2 different inputs
  //1. stateTaxCalculator takes COST_PRICE:Int as input
  //2. internationalTaxCalculator takes countryCode:String as input

  def internationalTax(country:String):Double = if(country == "UK") 0.3 else 0.4

  val countryReaderForInternationalTax = Reader(internationalTax);

  case class Config(costPrice:Int, countryCode:String)

  //Option 1 is to modify our functions to take CombinedConfig as a parameter

  val globalConfigReaderForStateTax = Reader(stateTaxCalculator).local((c:Config)=>c.costPrice)

  val globalConfigReaderForInternationalTax = Reader(internationalTax).local((c:Config)=>c.countryCode)

  val totalTax = for{
    salesTax <- globalConfigReaderForStateTax

    internationalTax <- globalConfigReaderForInternationalTax

  } yield(salesTax + internationalTax)

  totalTax(Config(costPrice=1000, countryCode = "US"))

}


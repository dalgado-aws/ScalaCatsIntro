package toppackage

import cats.Monad

class ComposingFunctionsWithFunctors {

  /***
   * Removes whitespace from input String
   */
  def removeWhiteSpace(dateString:String):String= ???

  /***
   * Given a string in yyyy/mm/dd format, return the year as int
   * For e.g. extractYearFromDateString("2010/11/11") will return 2010
   */
  def extractYearFromDateString(str:String):Int =  ???

  def checkYearAfter2000(year:Int):Boolean = year > 2000

  def birthYearAfter2000(dateString:Option[String]):Boolean = dateString.
    map(removeWhiteSpace).
    map(extractYearFromDateString).
    map(checkYearAfter2000).get


   def testBirthYear: Unit = {
     val firstBirthYearAfter200 = birthYearAfter2000(Option(" 2222/11/22"))

     val secondBirthYearAfter200 = birthYearAfter2000(Option("/11/22"))
   }
}

class ComposingFunctionsWithMonads {

  /***
   * Removes whitespace from input String
   */
  def removeWhiteSpace(dateString:String):Option[String]= ???

  /***
   * Given a string in yyyy/mm/dd format, return the year as int
   * For e.g. extractYearFromDateString("2010/11/11") will return 2010
   */
  def extractYearFromDateString(str:String):Option[Int] =  {
    if (true) //if we are able to extract year successfully, we will return Option(yearInt)
      Option(2001)
    else
      None // None is a subclass of Option
  }

  def checkYearAfter2000(year:Int):Option[Boolean] = Option(year > 2000)

  def birthYearAfter2000(dateString:Option[String]):Boolean = dateString.
    flatMap(removeWhiteSpace).
    flatMap(extractYearFromDateString).
    flatMap(checkYearAfter2000).get

  def testBirthYear: Unit = {
    val firstBirthYearAfter200 = birthYearAfter2000(Option(" 2222/11/22"))

    val secondBirthYearAfter200 = birthYearAfter2000(Option("/11/22"))
  }
}

object ChainingWithErrorHandling {

  trait Container[+A] {

    def flatMap[B](f:A=>Container[B]):Container[B]

    def map[B](fn:A=>B):Container[B]

    def getOr[B>:A](v:B):B
  }

  class GoodContainer[+A](val data:A) extends Container[A] {

    override def flatMap[B](f:A=>Container[B]):Container[B] = f(data)

    // writing map in terms of flatMap
    def map[B](fn:A=>B):Container[B] = this.flatMap(i=>new GoodContainer[B](fn(i)))

    override def getOr[B>:A](v: B): B = data
  }

  class ErrorContainer[+A] extends Container[A] {

    override def flatMap[B](f: A => Container[B]): Container[B] = new ErrorContainer[B]

    def map[B](fn:A=>B):Container[B] = new ErrorContainer[B]()

    override def getOr[B>:A](v: B): B = v
  }

  def fx1(i:Int):Container[Int] = if(true) new GoodContainer(100) else new ErrorContainer

  def fx2(i:Int):Container[Int] = if(true) new GoodContainer(100) else new ErrorContainer

  val resultOfChaining:Int = new GoodContainer(1).flatMap(fx1).flatMap(fx2).getOr(3)

  // special syntax for chaining
  def specialSyntaxForClassesThatHaveMapAndFlatMap  =
    for {
      a <- new GoodContainer(100)
         b <- new GoodContainer(200)
    }
   yield a + b
}

object MakingContainerAMonadUsingCatsTypeClasses {

  class ContainerV1[+A](val item:A)

  val monadInstanceForContainer = new Monad[ContainerV1] {
    override def flatMap[A, B](fa: ContainerV1[A])(f: A => ContainerV1[B]): ContainerV1[B] = f(fa.item)

    override def tailRecM[A, B](a: A)(f: A => ContainerV1[Either[A, B]]): ContainerV1[B] = ???

    override def pure[A](x: A): ContainerV1[A] = new ContainerV1(x)
  }
}



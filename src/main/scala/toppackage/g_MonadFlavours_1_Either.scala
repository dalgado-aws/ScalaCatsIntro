package toppackage

import cats.implicits._

object TestingEither {

  val result = for {
    a <- Right(1)
    b <- Right(1)
  }
    yield a + b

  val f1 = (x:Int) => Right(x + 1)
  val f2 = (x:Int) => Right(x + 2)
  val f3_cannot_process_input = (x:Int) => Left("Error")
  val f3 = (x:Int) => Right(x + 3)

  val chain = Right(1).flatMap(f1).flatMap(f2).flatMap(f3_cannot_process_input).flatMap(f3)
  val defaultOnError = -1
  val isError = chain.isLeft
  val errorValue = chain.get(1)

  33.asRight[String]
}



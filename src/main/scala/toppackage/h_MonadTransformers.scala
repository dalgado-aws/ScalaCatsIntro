package toppackage

import cats.data.OptionT
import cats.implicits._

object TestingMonadTransformers extends App {

  val resultOne:Either[String, Option[Int]] = Right(Option(1))

  val resultTwo:Either[String, Option[Int]] = Right(Option(2))

  val result = for {
    r1 <- OptionT(resultOne)

    r2 <- OptionT(resultTwo)

  } yield r1 + r2

  println(result.value)
}

package  toppackage

import cats.{Id, Monad}
import cats.implicits._

object TestingId extends App {

  val i:Id[Int] = 22

  val j = 33:Id[Int]

  val k:Id[Int] = Monad[Id].flatMap(i){x:Int => 33}

  Monad[Id].pure(21).flatMap(x=>x+2)
}

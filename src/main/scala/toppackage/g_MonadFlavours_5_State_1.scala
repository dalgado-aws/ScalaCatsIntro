package toppackage

import cats.data.State

object TestingState_1 extends  App {

  val firstFunction = (s:Int) => (s+1, 1)

  val secondFunction = (s:Int) => (s+2, 1)

  val thirdFunction = (s:Int) => (s+3, 1)

  val needledState = for{
    i <- State(firstFunction)

    j <- State(secondFunction)

    k <- State(thirdFunction)
  } yield(i, j, k)

  print("state running", needledState.run(0).value)

  val a = State(firstFunction)

  val b = State(secondFunction)

  val c = State(thirdFunction)

  a.flatMap(as => b.flatMap(bs => c.map( cs => s"final State ${cs}")))
}


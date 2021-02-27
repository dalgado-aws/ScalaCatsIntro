package toppackage

import cats.data.State

object TestingState_2 extends App {

  val stateTransitionOnArithmeticOperation: String => List[Int] => (List[Int], Int) = (operation:String) => (state:List[Int]) => {

    println(s"working with data ${state} and operation ${operation}")

    state match  {

      case a :: b :: rest =>

        println(s"executing action for ${operation} on state ${a} ${b} ${rest}")

        val d = operation // will not be  used .... hardcoding to +

        val result = a + b

        val newState = result :: rest

        (newState, result)

      case _ =>

        println(s"executing action for ${operation} without 3 element state")

        (List(), 0)
    }
  }


  val stateTransitionOnNumber:Int => List[Int] => (List[Int], Int) = (number:Int) =>  (state:List[Int]) => {

    println(s"working with data ${state} and number ${number} .... will return ${number::state}")

    (number::state,0)
  }


  def processStateWithInputToCreateNewState(thisState:State[List[Int], Int], inputChar:Any):State[List[Int], Int] = {

    val action = inputChar match {

      case operation:String => {

        println(s"returning action for operation ${operation}")

        State(stateTransitionOnArithmeticOperation(operation))

      }
      case number:Int => {

        println(s"returning action for number ${number}")

        State(stateTransitionOnNumber(number))}
    }
    action
  }


  val program =  List(100, 200, 300 , "+", "+")

  import cats.data._

  val zeroState = State.pure[List[Int], Int](0)

  val programResult: State[List[Int], Int] = program.foldLeft(zeroState)((s:State[List[Int], Int], item:Any)=> s.flatMap(_ => processStateWithInputToCreateNewState(s,item)))

  println("*"*73)

  programResult.run(List())

  println(s"program result is ${programResult.run(List()).value}")
}

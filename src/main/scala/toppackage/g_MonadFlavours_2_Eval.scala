package toppackage

import cats.Eval

object TestingEval extends  App {

  //just like "val"
  val now = Eval.now {
    println("now")
    val a = 100
    val b = 100
    a + b
  }

  //just like "lazy val"
  val later = Eval.later {
    println("later")
    val a = 100
    val b = 100
    a + b
  }

  //just like "def"
  val always = Eval.always {
    println("always")
    val a = 100
    val b = 100
    a + b
  }

  val result = now.flatMap(i=>later.flatMap(j=> always.map(k=> i+j+k)))

  println("*************************************************************************")
  println("first call", result.value)
  println("*************************************************************************")
  println("second call", result.value)
  println("*************************************************************************")
  println("third call", result.value)
}


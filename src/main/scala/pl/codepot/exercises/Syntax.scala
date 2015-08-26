package pl.codepot.exercises

import scala.concurrent.Future
import scala.util.Try
import scala.concurrent.ExecutionContext.Implicits.global
import reflect.runtime.universe.{ reify => desugar }

object Syntax extends App {

  /** T7.0
   * Why it is not compiling?
   * Not specific to for-comprehensions but could save you few minutes once
   */
  def notCompiling = {
    //    for (
    //      i <- List(1, 2, 3)
    //      j <- List(1, 2, 3)
    //    ) yield i + j
  }

  /**
   * T7.1
   * Why is this example compiling, while this generators have different shape?
   *
   * Hint: use desugar() and definition of flatMap
   */
  def whyIsItCompiling() = {

    for {
      a <- Future.successful(1)
      b <- Try(2)
      c <- Option(3)
      d <- Right(4).right
      e <- List(5)
      sum = a + b + c + d + e
    } println(sum)

  }

}

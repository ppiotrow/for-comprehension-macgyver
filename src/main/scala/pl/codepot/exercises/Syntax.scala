package pl.codepot.exercises

import scala.concurrent.Future
import scala.util.Try
import scala.concurrent.ExecutionContext.Implicits.global
import reflect.runtime.universe.{ reify => desugar }

object Syntax extends App {

  /**
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

  def filterWithFilterDifference = {

    val isLessThan3_sideeffect = (x: Int) => {
      print(x)
      x < 3
    }

    println(desugar {
      val l = for {
        a <- List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        if isLessThan3_sideeffect(a)
      } yield a
      l.take(1)
    })
    Stream.apply(1, 2, 3, 4).filter(isLessThan3_sideeffect).take(1)
    println()

    println(desugar {
      var found = false
      val z = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).withFilter(_ % 2 == 1 && !found).foreach(x => if (x == 5) found = true else println(x))
      print(z)
    })
    // val k = List(1, 2, 3, 4).withFilter(_ < 3).map(identity)
  }

  // filterWithFilterDifference
}

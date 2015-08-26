package pl.codepot.exercises

import scala.io.Source
import scala.util.{ Try, Failure }

object TryExample {

  /**
   * prove that Try invocation chain is stopped with first failure.
   *
   * Hint: use some visible side effect like print() to prove that other Try in not executed
   */
  def chain = for {
    v1 <- Try(throw new RuntimeException("It was my fault"))
    v2 <- Try {
      print("(Un)Expected side effect")
      Thread.sleep(10 * 1000)
      Source.fromURL("www.google.pl").mkString
    }
  } yield v2

  /**
   * Using String.toInt wrapped by Try implement division
   * division(a,b) = a/b
   * Use guard to not divide by 0
   * What is returned if b == 0 ?
   */
  def division(dividend: String, divisor: String): Try[Int] = for {
    a <- Try(dividend.toInt)
    b <- Try(divisor.toInt)
    if b != 0
  } yield a / b

  /**
   * What to do with exception in Try?
   *
   * Using this method exercise handling failures with
   *  pattern matching
   *  recover
   */
  def abc(): Try[Int] = for {
    a <- Try("1".toInt)
    b <- Try("0".toInt)
    division <- Try(a / b)
  } yield division

  /**
   * Enable options to work with Try.
   *
   * What if under Try object there is hidden exception?
   */
  def tryWithOption: Option[Int] = for {
    i <- Some(1)
    j <- Try(1).toOption
  } yield i + j
  tryWithOption
}

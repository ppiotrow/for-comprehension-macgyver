package pl.codepot.exercises

import pl.codepot.common.{ Espresso, Currency }

import reflect.runtime.universe.{ reify => desugar }

object IntroductionExample extends App {

  type ExchangeRate = Double
  val euro = Currency("EUR")
  val dollar = Currency("USD")
  val rates: Map[Currency, Map[Currency, ExchangeRate]] = Map(euro -> Map(dollar -> 1.058))

  /**
   * Rewrite following code so it will use for-comprehensions.
   * Is it more readable?
   */
  def convertEuroToUsd(amount: Double): Unit = {
    val euroExchangeRates: Option[Map[Currency, ExchangeRate]] = rates.get(euro)
    val eurDollarRate: Option[ExchangeRate] = euroExchangeRates.flatMap(_.get(dollar))
    eurDollarRate.map(_ * amount).foreach(inDollars => print(s"$amount EUR is $inDollars USD"))
  }

  /**
   * Rewrite following code so it will use for-comprehensions.
   * Is it more readable?
   */
  def patternMach(amount: Double): Unit = rates.get(euro) match {
    case Some(euroExchangeRates) =>
      euroExchangeRates.get(dollar) match {
        case Some(eurDollarRate) =>
          val inDollars = amount * eurDollarRate
          print(s"$amount EUR is $inDollars USD")
        case None => ()
      }
    case None => ()
  }

  /**
   * Use for-comprehensions to print the table containing the currency name and it's exchange rates.
   * Is it better than partial anonymous functions passed to foreach blocks?
   * EUR
   *   DOL -> 1.058
   *   ...
   * DOL
   *   EUR -> ....
   *   ....
   */
  def printExchangeRates() = {
    rates.foreach {
      case (from, toRates) =>
        toRates.foreach {
          case (to, rate) =>
            println(s"$from")
            println(s"   $to -> $rate")
        }
    }
  }

  /**
   * Play with desugaring to see what are really following code blocks
   */
  def desugaringFun = {

    val odd = List(1, 3, 5, 7)
    val neightbours = (i: Int) => List(i - 1, i, i + 1)

    //println(desugar {
    for {
      i <- odd
    } yield i * 2
    //  }.tree)

    //    println(desugar {
    //      for {
    //        i <- odd
    //        j <- neightbours(i)
    //      } print(j)
    //    }.tree)

    //    println(desugar {
    //      for {
    //        i <- odd
    //        j <- neightbours(i)
    //        if j % 2 == 0
    //      } print(j)
    //    }.tree)

    /**this code after desugaring is not much readable. Could you clear it a little bit?**/
    //    for {
    //      i <- Some(1)
    //      j = i + 1
    //    } yield j

  }
  desugaringFun
}

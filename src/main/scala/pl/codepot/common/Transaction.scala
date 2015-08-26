package pl.codepot.common

import scala.concurrent.Future
import scala.util.Try
import scala.concurrent.ExecutionContext.Implicits.global

case class Currency(name: String)
case class TransactionId(value: String)
case class Transaction(from: String, to: String, amount: Double, currency: Currency)

object Transaction {
  def fromLine(line: String): Try[Transaction] = Try {
    val cells = line.split("""\t""")
    Transaction(cells(0), cells(1), cells(2).toLong, Currency(cells(3)))
  }
}

object TransactionService {
  def get(id: TransactionId): Future[Transaction] = Future {
    Thread.sleep(50)
    Transaction("const", "const2", 1234l, Currency("PLN"))
  }
}

object CurrencyService {
  import WebServiceData._
  import scala.concurrent._
  import scala.concurrent.ExecutionContext.Implicits.global

  def convertToEUR(amount: Double, c: Currency): Future[Double] = {
    Future {
      val currRate = euroRate(c.name)
      Thread.sleep(10)
      amount * currRate
    }
  }

  def convertPlnTo(amountOfPLN: Double, c: Currency): Future[Double] = {
    Future {
      val currRate = plnRate(c.name)
      Thread.sleep(10)
      amountOfPLN * currRate
    }
  }

  def euroRates: Future[Map[Currency, Double]] = Future {
    Thread.sleep(10)
    euroRate.map { case (k, v) => (Currency(k), v) }
  }

}

object WebServiceData {
  val euroRate = Map("PLN" -> 0.24, "USD" -> 0.73, "HUF" -> 0.003, "EUR" -> 1.00)
  val plnRate = Map("PLN" -> 1.00, "USD" -> 0.27, "HUF" -> 73.35, "EUR" -> 0.24)
}

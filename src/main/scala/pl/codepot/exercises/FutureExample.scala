package pl.codepot.exercises

import akka.actor.{ Props, ActorSystem, Actor }
import akka.util.Timeout
import pl.codepot.common._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{ Await, Future }

object FutureExample {

  /**
   * T5.0
   * Convert transaction to be in EUR
   * Assume that given transaction id is for transaction in different currency than EUR
   *
   * Experiment with deconstructors and alias
   * Hint:
   * use
   *     TransactionService.get
   *     CurrencyService.convertToEUR
   */
  def convertToEur(id: TransactionId): Future[Transaction] = for {
    t @ Transaction(_, _, amount, currency) <- TransactionService.get(id)
    converted <- CurrencyService.convertToEUR(amount, currency)
  } yield t.copy(currency = Currency("EUR"), amount = converted)

  /**
   * T5.1
   * Print given amount(PLN) in USD and EUR
   *
   * Hint:
   * Is following execution really done in parallel?
   */
  def makeItFaster(amount: Double): Future[Unit] = {
    val usd = Currency("USD")
    val eur = Currency("EUR")
    for {
      inUSD <- CurrencyService.convertPlnTo(amount, usd)
      inEUR <- CurrencyService.convertPlnTo(amount, eur)
      _ = println(s"$amount PLN is $inUSD USD or $inEUR EUR")
    } yield ()
  }
  //  makeItFaster(10.0)

  /**
   * T5.2
   * How to handle guard?
   *
   * Hint:
   * Is `result`filled with Success or Failure?
   * How to recover() from failure? Especialy from NoSuchElementException?
   */
  def futureGuardProblem = {
    val result = for {
      number <- Future.successful(1)
      if number < 1
    } yield number
    //print(Await.result(result, 5.seconds))
  }

  /**
   * T5.3
   * Examine the execution time of the example with and without guard
   * @return
   */
  def breakChain = {
    def sleepAndGet[T](seconds: Int, value: T) = Future {
      Thread.sleep(seconds * 1000)
      value
    }
    val result = for {
      a <- sleepAndGet(1, "a")
      //if a.startsWith("prefix")
      b <- sleepAndGet(5, "b")
      c <- sleepAndGet(10, "c")
    } yield a + b + c
    Await.ready(result, 30.seconds)
  }
  //  breakChain

  /**
   * T5.4
   * How to compose future[Option[T] in one for-comprehension
   *
   * Return projects of author of given commit id
   * Hint:
   * Use deconstruction
   */
  def futureOption(): Future[List[String]] = {
    val sha = SHA("1213123213123")
    def projects(author: Commit.Author) = Future.successful(List("scala/scala", "akka/akka", "EnterpriseQualityCoding/FizzBuzzEnterpriseEdition"))

    for {
      Some(remoteCommit) <- RemoteGitClient.get(sha)
      author = remoteCommit.author
      j <- projects(author)
    } yield j
  }

  /**
   * T5.5
   * Use optionT from scalaz.OptionT._ to compose two Future[Option[
   *
   * Hint:
   *  RemoteGitClient.get()
   * I recommend just google stackoverflow
   * @return
   */
  def futureOptionScalaZ = {
    import scalaz._
    import Scalaz._
    import scalaz.OptionT._
    val shaF = Future.successful(Option(SHA("1213123213123")))

    (for {
      i <- optionT(shaF)
      j <- optionT(RemoteGitClient.get(i))
    } yield j).run
  }

  /**
   * T5.5
   * If world is beautiful place then ask your boss for money and print what you received.
   * Use akka.pattern.ask to combine aktor responses together
   */
  def akkaFun = {
    import akka.pattern.ask
    import scala.concurrent.duration._
    val system = ActorSystem()
    implicit val timeout = Timeout(1.second)
    val omniscient = system.actorOf(Props(new Actor {
      def receive = {
        case "Is the world beautiful place?" => sender() ! true
      }
    }))
    val boss = system.actorOf(Props(new Actor {
      def receive = {
        case "Money!" => sender() ! ":)"
      }
    }))

    for {
      answer <- ask(omniscient, "Is the world beautiful place?").mapTo[Boolean]
      if answer
      money <- ask(boss, "Money!").mapTo[String]
    } println(s"My boss gave me $money because world is beautiful place")

    ///
    system.shutdown()
  }

}

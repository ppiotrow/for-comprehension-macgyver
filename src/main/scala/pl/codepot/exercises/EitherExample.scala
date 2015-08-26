package pl.codepot.exercises

import pl.codepot.common._

object EitherExample {
  /**
   * Using water and coffee beans produce espresso
   * and then mix it with cookies to have delicious tiramisu
   *
   * REMEMBER: The best tiramisu is from sweetened espresso
   *
   * Hint:
   * use CoffeeMachine.espresso
   * use TiramisuFactory.create
   */
  def tiramisuMaker(water: Water, beans: Beans, cookies: Cookies): Either[String, Tiramisu] = for {
    espresso <- CoffeeMachine.espresso(water, beans).right
    sweetEspresso <- Right(espresso.sweeten).right
    tiramisu <- TiramisuFactory.create(cookies, sweetEspresso).right
  } yield tiramisu

  /**
   * Combine Option with Either in for comprehension
   *
   * Hint:
   * http://www.scala-lang.org/api/current/index.html#scala.Option
   */
  def optionToEither(cookies: Cookies): Either[String, Tiramisu] = {
    val espresso: Option[Espresso] = Option(Espresso(isSweet = true))

    for {
      sweetEspresso <- espresso.toRight("No espresso found").right
      tiramisu <- TiramisuFactory.create(cookies, sweetEspresso).right
    } yield tiramisu

  }

  /**
   * Return espresso only if it is sweet.
   *
   * Is it possible to use guard with Either?
   *
   */
  def guardFun(water: Water, beans: Beans): Either[String, Espresso] = for {
    espresso <- CoffeeMachine.espresso(water, beans).right
  } yield espresso

  def disjunction = ???
}

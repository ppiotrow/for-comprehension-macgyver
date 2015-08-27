package pl.codepot.common

case class Water(temperature: Int)

case class Beans(kind: String)

case class Espresso(isSweet: Boolean = false) {
  def sweeten = copy(isSweet = true)
}
case class Cookies(number: Int)
case class Tiramisu()

object CoffeeMachine {
  def espresso(water: Water, beans: Beans): Either[String, Espresso] = {
    if (water.temperature > 100)
      Left("Water too hot")
    else if (water.temperature < 93)
      Left("Water too cold")
    else if (!beans.kind.equals("Robusta"))
      Left("Wrong type of beans")
    else
      Right(Espresso())
  }
}

object TiramisuFactory {
  def create(cookies: Cookies, espresso: Espresso): Either[String, Tiramisu] = {
    if (cookies.number < 10)
      Left("Not enough cookies")
    else if (!espresso.isSweet)
      Left("You should use sweet espresso")
    else
      Right(Tiramisu())
  }
}


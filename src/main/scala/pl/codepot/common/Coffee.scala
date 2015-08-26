package pl.codepot.common

case class Water(temperature: Int)

case class Beans(kind: String)

case class Espresso(isSweet: Boolean = false) {
  def sweeten = copy(isSweet = true)
}

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

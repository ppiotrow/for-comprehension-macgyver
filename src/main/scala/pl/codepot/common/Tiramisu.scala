package pl.codepot.common

case class Cookies(number: Int)
case class Tiramisu()

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

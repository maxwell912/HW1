package converter
import converter.Currencies._
import converter.CurrencyConverter

object Test {
  def main(args: Array[String]) = {
    val usdRubRates = Map(
      USD -> Map(RUB -> BigDecimal(72.5)),
      RUB -> Map(USD -> BigDecimal(1 / 72.5))
    )
    // Testing Money methods
    val a = Money(2, RUB)
    val b = Money(3, USD)
    val c = a + b
    println(c + "\n")

    // CurrencyConverter class testing
    val usdRubConverter = CurrencyConverter(usdRubRates)
    val exchangedRub = usdRubConverter.exchange(Money(2, USD), RUB)
    val exchangedUsd = usdRubConverter.exchange(Money(10, RUB), USD)
  }
}

package converter

import converter.Currencies._
import converter.Errors.{UnsupportedCurrencyException, WrongCurrencyException}

class CurrencyConverter(ratesDictionary: Map[String, Map[String, BigDecimal]]) {
  def exchange(money: Money, toCurrency: String): Money = {
    if (!SupportedCurrencies.contains(toCurrency)) throw new UnsupportedCurrencyException(toCurrency)
    else if (money.currency == toCurrency) throw new WrongCurrencyException(toCurrency)
    else {
      val exchange_rate = ratesDictionary(money.currency)(toCurrency)
      println(Money(money.amount * exchange_rate, toCurrency))
      Money(money.amount * exchange_rate, toCurrency)
    }
  }
}

object CurrencyConverter {
  def apply(ratesDictionary: Map[String, Map[String, BigDecimal]]): CurrencyConverter = {
    val fromCurrencies = ratesDictionary.keys
    val toCurrencies = ratesDictionary.values
    if (
      fromCurrencies.toSet.subsetOf(SupportedCurrencies) &&
        toCurrencies.forall(_.keys.toSet.subsetOf(SupportedCurrencies))
    )
      new CurrencyConverter(ratesDictionary)
    else throw new UnsupportedCurrencyException("None")
  }
}

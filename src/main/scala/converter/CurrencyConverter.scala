package converter

import Currencies.SupportedCurrencies
import converter.Errors.{UnsupportedCurrencyException, WrongCurrencyException}

class CurrencyConverter(ratesDictionary: Map[String, Map[String, BigDecimal]]) {
  def exchange(money: Money, toCurrency: String): Money = {
    if (money.currency != toCurrency) {
    val fromCurrencies = money.currency
    val rates = ratesDictionary.getOrElse(fromCurrencies, throw new UnsupportedCurrencyException)
      .getOrElse(toCurrency, throw new UnsupportedCurrencyException)
    val exchangedValue = rates * money.amount
    Money(exchangedValue, toCurrency)
  }
  else throw new WrongCurrencyException
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
    else throw new UnsupportedCurrencyException
  }
}

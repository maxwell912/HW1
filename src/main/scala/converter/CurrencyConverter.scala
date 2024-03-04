package converter

import Currencies.SupportedCurrencies
import converter.Errors.{UnsupportedCurrencyException, WrongCurrencyException}

class CurrencyConverter(ratesDictionary: Map[String, Map[String, BigDecimal]]) {
  def exchange(money: Money, toCurrency: String): Money = {
    if (money.currency != toCurrency){
      val fromCurrency = money.currency
      val rate = ratesDictionary.getOrElse(fromCurrency, throw new UnsupportedCurrencyException)
        .getOrElse(toCurrency, throw new UnsupportedCurrencyException)
      val exchangedAmount = money.amount * rate
      Money(exchangedAmount, toCurrency)
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

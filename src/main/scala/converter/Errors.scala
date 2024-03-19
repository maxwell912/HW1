package converter

object Errors {
  class MoneyAmountShouldBePositiveException(message: String) extends Exception(message)

  class UnsupportedCurrencyException(currency: String) extends Exception(s"Unsupported currency: $currency")

  class WrongCurrencyException(currency: String) extends Exception(s"You have two identical currencies: $currency")
  class DifferentCurrenciesException(currency_first: String, currency_second: String) extends Exception(s"You have two different currencies: $currency_first and $currency_second")
}

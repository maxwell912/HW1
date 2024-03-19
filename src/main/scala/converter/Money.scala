package converter

import converter.Currencies.SupportedCurrencies
import converter.Errors.{MoneyAmountShouldBePositiveException, UnsupportedCurrencyException, WrongCurrencyException, DifferentCurrenciesException}

case class Money private (amount: BigDecimal, currency: String) {
  def +(other: Money): Money = {
    if (isSameCurrency(other) == true) Money(amount + other.amount, other.currency)
    else throw new DifferentCurrenciesException(currency, other.currency)
  }
  def -(other: Money): Money = {
    if (isSameCurrency(other) == true) Money(amount - other.amount, other.currency)
    else throw new DifferentCurrenciesException(currency, other.currency)
  }
  def isSameCurrency(other: Money): Boolean = {
    if (other.currency == currency) true
    else false
  }
}

object Money {
  def apply(amount: BigDecimal, currency: String): Money = {
    if (amount < 0) {
      throw new MoneyAmountShouldBePositiveException("Money amount should be positive")
    }

    if (!SupportedCurrencies.contains(currency)) {
      throw new UnsupportedCurrencyException(currency)
    }

    new Money(amount, currency)
  }
}

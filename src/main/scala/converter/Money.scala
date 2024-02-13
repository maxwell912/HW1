package converter

import converter.Currencies.SupportedCurrencies
import converter.Errors.{MoneyAmountShouldBePositiveException, UnsupportedCurrencyException, WrongCurrencyException}

case class Money private (amount: BigDecimal, currency: String) {
  def +(other: Money): Money = ???
  def -(other: Money): Money = ???
  def isSameCurrency(other: Money): Boolean = ???
}

object Money {
  def apply(amount: BigDecimal, currency: String): Money = {
    if (amount < 0) {
      throw new MoneyAmountShouldBePositiveException
    }

    if (!SupportedCurrencies.contains(currency)) {
      throw new UnsupportedCurrencyException
    }

    new Money(amount, currency)
  }
}

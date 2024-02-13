package converter

object Errors {
  class MoneyAmountShouldBePositiveException extends Exception

  class UnsupportedCurrencyException extends Exception

  class WrongCurrencyException extends Exception
}

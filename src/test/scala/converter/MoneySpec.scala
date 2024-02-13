package converter

import converter.Currencies.{EUR, RUB, USD}
import converter.Errors.{MoneyAmountShouldBePositiveException, UnsupportedCurrencyException, WrongCurrencyException}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks._

class MoneySpec extends AnyFlatSpec with Matchers {
  "smart-constructor" should "create valid Money instance" in {
    val amountAndCurrency = Table(
      ("amount", "currency"),
      (10, USD),
      (20, EUR),
      (30, RUB)
    )

    forAll(amountAndCurrency) { (amount, currency) =>
      val money = Money(amount, currency)

      money.amount shouldEqual amount
      money.currency shouldEqual currency
    }
  }

  "smart-constructor" should "throw MoneyAmountShouldBePositiveException if amount is less than zero" in {
    Seq(
      -1,
      -5,
      -10
    ).foreach(amount =>
      assertThrows[MoneyAmountShouldBePositiveException] {
        Money(amount, EUR)
      }
    )
  }

  "smart-constructor" should "throw UnsupportedCurrencyException if currency is unsupported" in {
    Seq(
      "CNY",
      "AUD",
      "ALL",
      "DZD"
    ).foreach(currency =>
      assertThrows[UnsupportedCurrencyException] {
        Money(1, currency)
      }
    )
  }

  "plus operator" should "add money" in {
    val money1 = Money(2, USD)
    val money2 = Money(3, USD)

    money1 + money2 shouldEqual Money(5, USD)
  }

  "plus operator" should "have neutral element with 0 amount" in {
    val money = Money(10, USD)
    val zero = Money(0, USD)

    money + zero shouldEqual money
    zero + money shouldEqual money
  }

  "plus operator" should "throw if try to add different currencies" in {
    val money1 = Money(2, USD)
    val money2 = Money(3, EUR)

    assertThrows[WrongCurrencyException] {
      money1 + money2
    }
  }

  "minus operator" should "subtract money" in {
    val money1 = Money(5, USD)
    val money2 = Money(2, USD)

    money1 - money2 shouldEqual Money(3, USD)
  }

  "minus operator" should "have neutral element with 0 amount" in {
    val money = Money(10, USD)
    val zero = Money(0, USD)

    money - zero shouldEqual money
  }

  "minus operator" should "throw if try to add different currencies" in {
    val money1 = Money(5, USD)
    val money2 = Money(3, EUR)

    assertThrows[WrongCurrencyException] {
      money1 - money2
    }
  }
}

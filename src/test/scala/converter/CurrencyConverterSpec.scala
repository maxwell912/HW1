package converter

import converter.Currencies._
import converter.Errors.{UnsupportedCurrencyException, WrongCurrencyException}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CurrencyConverterSpec extends AnyFlatSpec with Matchers {
  private val RANDOM_CURRENCY = "RND"

  private val usdRubRates = Map(
    USD -> Map(RUB -> BigDecimal(72.5)),
    RUB -> Map(USD -> BigDecimal(1 / 72.5))
  )

  private val usdRubConverter = CurrencyConverter(usdRubRates)

  "exchange" should "convert money for supported currencies" in {
    val exchangedRub = usdRubConverter.exchange(Money(2, USD), RUB)
    val exchangedUsd = usdRubConverter.exchange(Money(10, RUB), USD)

    exchangedRub shouldEqual Money(145, RUB)
    exchangedUsd shouldEqual Money(BigDecimal(1 / 7.25), USD)
  }

  "exchange" should "throw WrongCurrencyException if try to convert to same currency" in {
    SupportedCurrencies.foreach(currency =>
      assertThrows[WrongCurrencyException] {
        usdRubConverter.exchange(Money(1, currency), currency)
      }
    )
  }

  "exchange" should "throw UnsupportedCurrencyException if currency is not in rates" in {
    Seq(
      EUR,
      RANDOM_CURRENCY
    ).foreach(currency => {
      assertThrows[UnsupportedCurrencyException] {
        usdRubConverter.exchange(Money(1, USD), currency)
      }

      assertThrows[UnsupportedCurrencyException] {
        usdRubConverter.exchange(Money(1, currency), USD)
      }
    })
  }

  "converted constructor" should "create converter even if rates are not symmetrical" in {
    val rates = Map(
      RUB -> Map(USD -> BigDecimal(85)),
      USD -> Map(EUR -> BigDecimal(1.1))
    )

    noException should be thrownBy CurrencyConverter(rates)
  }

  "converted constructor" should "throw UnsupportedCurrencyException if rates dictionary contains wrong currency" in {
    Seq(
      Map(
        RANDOM_CURRENCY -> Map(RUB -> BigDecimal(85))
      ),
      Map(
        RUB -> Map(RANDOM_CURRENCY -> BigDecimal(100))
      )
    ).foreach(rates => {
      assertThrows[UnsupportedCurrencyException] {
        CurrencyConverter(rates)
      }
    })
  }
}

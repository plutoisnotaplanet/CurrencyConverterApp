package com.plutoisnotaplanet.currencyconverterapp.application.utils

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

object CurrencyConversion {

    fun convertCurrency(fromRate: BigDecimal, toRate: BigDecimal): BigDecimal {
        val rateForOneDollar = convertAnyCurrencyToDollar(fromRate)
        return convertDollarToAnyCurrency(rateForOneDollar, toRate)
    }

    private fun convertAnyCurrencyToDollar(fromRate: BigDecimal): BigDecimal {
        return BigDecimal.ONE.divide(fromRate, MathContext.DECIMAL128)
    }

    private fun convertDollarToAnyCurrency(dollarValue: BigDecimal, toRate: BigDecimal): BigDecimal {
        return toRate.divide(dollarValue, MathContext.DECIMAL128)
    }
}
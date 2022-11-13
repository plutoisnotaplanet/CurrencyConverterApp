package com.plutoisnotaplanet.currencyconverterapp.application.utils

import java.math.BigDecimal
import java.math.RoundingMode

object CurrencyConversion {

    fun convertCurrency(amount: BigDecimal, fromRate: Double, toRate: Double): Double {
        val valueInDollars = convertAnyCurrencyToDollar(amount, fromRate)
        return convertDollarToAnyCurrency(valueInDollars, toRate).toDouble()
    }

    private fun convertAnyCurrencyToDollar(amount: BigDecimal, fromRate: Double): BigDecimal {
        val scale = 50
        return amount.divide(BigDecimal.valueOf(fromRate), scale, RoundingMode.HALF_UP)
    }

    private fun convertDollarToAnyCurrency(dollarValue: BigDecimal, toRate: Double): BigDecimal {
        return dollarValue.multiply(BigDecimal.valueOf(toRate))
    }
}
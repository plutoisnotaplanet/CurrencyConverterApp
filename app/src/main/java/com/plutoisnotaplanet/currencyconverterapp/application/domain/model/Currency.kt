package com.plutoisnotaplanet.currencyconverterapp.application.domain.model

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

data class Currency(
    val code: String,
    val countryName: String,
    val rate: BigDecimal,
    val isFavorite: Boolean
) {

    fun toCurrencyViewItem(selectedCurrencyCode: String): CurrencyViewItem {
        return CurrencyViewItem(
            code = code,
            countryName = countryName,
            rate = conversionText(selectedCurrencyCode),
            isFavorite = isFavorite
        )
    }

    private val decimalFormatter: DecimalFormat
    private val decimalSeparator: String

    init {
        val numberFormatter = NumberFormat.getNumberInstance(Locale.getDefault())
        val conversionPattern = "#,##0.####"
        decimalFormatter = numberFormatter as DecimalFormat
        decimalFormatter.applyPattern(conversionPattern)
        decimalSeparator = decimalFormatter.decimalFormatSymbols.decimalSeparator.toString()
    }


    private val conversionText: (String) -> String
        inline get() = { currencyCode ->
            val scale = if (currencyCode == "BTC" || code == "BTC") 15 else 6
            formatConversion(rate.roundToFourDecimalPlaces(scale).toString())
        }

    private fun formatConversion(conversion: String): String {
        return when {
            conversion.contains(".") -> {
                val splitConversion = conversion.split(".")
                val wholePart = splitConversion[Order.FIRST.position]
                val decimalPart = splitConversion[Order.SECOND.position]
                decimalFormatter.format(BigDecimal(wholePart)) + decimalSeparator + decimalPart
            }
            else -> {
                decimalFormatter.format(BigDecimal(conversion))
            }
        }
    }

    private fun BigDecimal.roundToFourDecimalPlaces(scale: Int): BigDecimal = setScale(scale, RoundingMode.HALF_DOWN)
}



enum class Order(val position: Int) {
    FIRST(0),
    SECOND(1);
}
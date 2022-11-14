package com.plutoisnotaplanet.currencyconverterapp.application.data.db.entitites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.plutoisnotaplanet.currencyconverterapp.application.data.db.CurrencyConverterDatabase
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.Currency
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

@Entity(tableName = CurrencyConverterDatabase.CURRENCY_TABLE)
data class CurrencyEntity(
    @PrimaryKey
    @ColumnInfo(name = "column_currencyCode")
    val currencyCode: String,
    @ColumnInfo(name = "column_exchangeRate")
    var exchangeRate: Double
) {

    @ColumnInfo(name = "column_isFavorite")
    var isFavorite = false

//    @Ignore
//    var conversion = Conversion(BigDecimal.ZERO)

    @Ignore
    private var decimalFormatter: DecimalFormat

    @Ignore
    private var decimalSeparator: String

    init {
        val numberFormatter = NumberFormat.getNumberInstance(Locale.getDefault())
        val conversionPattern = "#,##0.####"
        decimalFormatter = numberFormatter as DecimalFormat
        decimalFormatter.applyPattern(conversionPattern)
        decimalSeparator = decimalFormatter.decimalFormatSymbols.decimalSeparator.toString()
    }

    fun toModel(): Currency {
        return Currency(
            currencyCode,
            exchangeRate,
            isFavorite
        )
    }

    /**
     * Currency code without the "USD_" prefix.
     * Example: USD_EUR -> EUR
     */
    val trimmedCurrencyCode
        get() = currencyCode.substring(CURRENCY_CODE_START_INDEX)

    override fun equals(other: Any?): Boolean {
        if (other !is CurrencyEntity) return false
        if (currencyCode != other.currencyCode) return false
        return true
    }

    override fun hashCode() = currencyCode.hashCode()

    /**
     * Since the toString() method is really only useful for debugging I've structured it in a way
     * which concisely displays the object's state.
     *
     * Example: 4 S* F* EUR
     *          | |  |   |
     *      Order |  |   |
     *     Selected? |   |
     *         Focused?  |
     *            Currency code
     *
     *    *blank if not selected/focused
     */
    override fun toString() = buildString {
        append("{")
        append(" ")
        append(trimmedCurrencyCode)
        append("}")
    }
//
//    inner class Conversion(conversionValue: BigDecimal) {
//        /**
//         * The underlying numeric conversion result.
//         * Example: 1234.5678
//         */
//        var conversionValue: BigDecimal = conversionValue
//            set(value) {
//                field = value.roundToFourDecimalPlaces()
//                conversionString = field.toString()
//            }
//
//        /**
//         * The [conversionValue] as a String.
//         * Example: "1234.5678"
//         */
//        var conversionString = String.EMPTY
//
//        /**
//         * The [conversionString] formatted according to locale.
//         * Example:    USA: 1,234.5678
//         *          France: 1.234,5678
//         */
//        val conversionText: String
//            get() {
//                return if (conversionString.isNotBlank()) {
//                    formatConversion(conversionString)
//                } else {
//                    String.EMPTY
//                }
//            }
//
//        /**
//         * The hint displayed when [conversionText] is empty.
//         */
//        var conversionHint = String.EMPTY
//            set(value) {
//                field = formatConversion(BigDecimal(value).toString())
//            }
//
//        /**
//         * Formats a numeric String with grouping separators while retaining trailing zeros.
//         */
//        private fun formatConversion(conversion: String): String {
//            return when {
//                conversion.contains(".") -> {
//                    val splitConversion = conversion.split(".")
//                    val wholePart = splitConversion[FIRST.position]
//                    val decimalPart = splitConversion[SECOND.position]
//                    decimalFormatter.format(BigDecimal(wholePart)) + decimalSeparator + decimalPart
//                }
//                else -> {
//                    decimalFormatter.format(BigDecimal(conversion))
//                }
//            }
//        }
//    }

    companion object {
        const val CURRENCY_CODE_START_INDEX = 4
    }
}
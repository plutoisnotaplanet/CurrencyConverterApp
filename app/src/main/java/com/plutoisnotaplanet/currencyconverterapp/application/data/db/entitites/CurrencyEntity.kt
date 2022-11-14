package com.plutoisnotaplanet.currencyconverterapp.application.data.db.entitites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.plutoisnotaplanet.currencyconverterapp.application.data.db.CurrencyConverterDatabase
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.Currency
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.CurrencyViewItem
import java.math.BigDecimal

@Entity(tableName = CurrencyConverterDatabase.CURRENCY_TABLE)
data class CurrencyEntity(
    @PrimaryKey
    @ColumnInfo(name = "column_currencyCode")
    val currencyCode: String,
    @ColumnInfo(name = "column_exchangeRate")
    var exchangeRate: Double,
) {

    @ColumnInfo(name = "column_isFavorite")
    var isFavorite = false

    @ColumnInfo(name = "column_countryName")
    var countryName = ""


    fun toModel(): Currency {
        return Currency(
            code = currencyCode,
            countryName = countryName.takeIf { it.isNotBlank() } ?: currencyCode,
            rate = BigDecimal(exchangeRate),
            isFavorite = isFavorite
        )
    }

    fun toCurrencyViewItem(): CurrencyViewItem {
        return CurrencyViewItem(
            code = currencyCode,
            countryName = countryName.takeIf { it.isNotBlank() } ?: currencyCode,
            rate = BigDecimal(exchangeRate).toPlainString(),
            isFavorite = isFavorite
        )
    }
}


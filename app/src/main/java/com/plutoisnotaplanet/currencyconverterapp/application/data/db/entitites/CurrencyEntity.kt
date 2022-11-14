package com.plutoisnotaplanet.currencyconverterapp.application.data.db.entitites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.plutoisnotaplanet.currencyconverterapp.application.data.db.CurrencyConverterDatabase
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.Currency
import java.math.BigDecimal
import java.math.RoundingMode
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



    fun toModel(): Currency {
        return Currency(
            currencyCode,
            BigDecimal(exchangeRate),
            isFavorite
        )
    }
}


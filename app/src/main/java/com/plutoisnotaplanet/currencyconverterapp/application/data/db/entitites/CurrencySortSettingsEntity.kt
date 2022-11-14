package com.plutoisnotaplanet.currencyconverterapp.application.data.db.entitites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.plutoisnotaplanet.currencyconverterapp.application.data.db.CurrencyConverterDatabase
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.CurrencyViewItem
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.SortBy
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.SortSettings

@Entity(tableName = CurrencyConverterDatabase.CURRENCY_SORT_SETTINGS_TABLE)
data class CurrencySortSettingsEntity(
    @PrimaryKey
    @ColumnInfo(name = "column_sortSettingsId")
    val id: Int,
    @ColumnInfo(name = "column_selectedCurrencyCode")
    val currencyCode: String,
    @ColumnInfo(name = "column_sortByName")
    val sortByName: Int,
    @ColumnInfo(name = "column_sortByRate")
    val sortByRate: Int
) {

    fun toSortSettings(currency: CurrencyViewItem): SortSettings {
        val sortList = SortBy.values()
        return SortSettings(
            selectedCurrency = currency,
            sortByName = sortList[sortByName],
            sortByRate = sortList[sortByRate]
        )
    }

}
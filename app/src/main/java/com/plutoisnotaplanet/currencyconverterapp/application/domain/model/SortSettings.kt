package com.plutoisnotaplanet.currencyconverterapp.application.domain.model

import androidx.compose.runtime.Stable
import com.plutoisnotaplanet.currencyconverterapp.application.data.db.entitites.CurrencySortSettingsEntity

@Stable
data class SortSettings(
    val selectedCurrency: Currency,
    val sortByName: SortBy,
    val sortByRate: SortBy
) {

    fun toCurrencySortSettingsEntity(id: Int): CurrencySortSettingsEntity {
        return CurrencySortSettingsEntity(
            id = id,
            currencyCode = selectedCurrency.name,
            sortByName = sortByName.ordinal,
            sortByRate = sortByRate.ordinal,
        )
    }

    val isSortActive: Boolean
        get() = isSortByNameActive || isSortByRateActive || isCurrencyNotDefault

    val isSortByNameActive: Boolean
        get() = sortByName != SortBy.None

    val isSortByRateActive: Boolean
        get() = sortByRate != SortBy.None

    val isCurrencyNotDefault: Boolean
        get() = selectedCurrency.isNotDefault
}
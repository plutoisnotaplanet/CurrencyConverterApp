package com.plutoisnotaplanet.currencyconverterapp.application.domain.model

import com.plutoisnotaplanet.currencyconverterapp.application.data.db.entitites.CurrencySortSettingsEntity

data class SortSettings(
    val selectedCurrency: Currency,
    val sortByName: SortBy,
    val sortByRate: SortBy
) {

    companion object {
        fun getEmpty(): SortSettings = SortSettings(Currency("USD",1.0, false), SortBy.None, SortBy.None)
    }

    fun toCurrencySortSettingsEntity(id: Int): CurrencySortSettingsEntity {
        return CurrencySortSettingsEntity(
            id = id,
            currencyCode = selectedCurrency.name,
            sortByName = sortByName.ordinal,
            sortByRate = sortByRate.ordinal,
        )
    }

    val isSortActive: Boolean
        get() = isSortByNameActive || isSortByRateActive || isCountrySelected

    val isSortByNameActive: Boolean
        get() = sortByName != SortBy.None

    val isSortByRateActive: Boolean
        get() = sortByRate != SortBy.None

    val isCountrySelected: Boolean
        get() = selectedCurrency.isNotDefault
}
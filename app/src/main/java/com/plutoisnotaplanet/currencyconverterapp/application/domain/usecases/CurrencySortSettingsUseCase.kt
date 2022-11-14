package com.plutoisnotaplanet.currencyconverterapp.application.domain.usecases

import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.*

interface CurrencySortSettingsUseCase {

    suspend fun updateSortSettings(listType: CurrencyListType, settings: SortSettings): Response<Unit>

    suspend fun updateSelectedCurrency(listType: CurrencyListType, currency: CurrencyViewItem): Response<Unit>

}
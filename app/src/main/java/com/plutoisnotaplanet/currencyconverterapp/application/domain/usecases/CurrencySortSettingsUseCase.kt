package com.plutoisnotaplanet.currencyconverterapp.application.domain.usecases

import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.Currency
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.CurrencyListType
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.Response
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.SortSettings

interface CurrencySortSettingsUseCase {

    suspend fun updateSortSettings(listType: CurrencyListType, settings: SortSettings): Response<Unit>

    suspend fun updateSelectedCurrency(listType: CurrencyListType, currency: Currency): Response<Unit>

}
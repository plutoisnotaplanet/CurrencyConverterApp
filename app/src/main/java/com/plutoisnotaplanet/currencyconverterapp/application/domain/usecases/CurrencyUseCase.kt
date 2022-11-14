package com.plutoisnotaplanet.currencyconverterapp.application.domain.usecases

import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.*
import com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.currency_list.CurrencyScreenUiState
import kotlinx.coroutines.flow.Flow

interface CurrencyUseCase {

    suspend fun updateCurrencies(): Response<Unit>

    suspend fun getSortedCurrenciesByListType(listType: CurrencyListType, queryText: String): Flow<CurrencyScreenUiState>

    suspend fun changeFavoriteCurrencyState(currency: CurrencyViewItem): Response<Unit>

    fun prepareSortedCurrenciesList(currencies: List<Currency>, sortSettings: SortSettings): List<Currency>
}
package com.plutoisnotaplanet.currencyconverterapp.application.domain.usecases

import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.Currency
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.CurrencyListType
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.Response
import com.plutoisnotaplanet.currencyconverterapp.ui.main.MainUiState
import kotlinx.coroutines.flow.Flow

interface CurrencyUseCase {

    suspend fun updateCurrencies(): Response<Unit>

    suspend fun getSortedCurrenciesByListType(listType: CurrencyListType, queryText: String): Flow<MainUiState>

    suspend fun changeFavoriteCurrencyState(currency: Currency): Response<Unit>
}
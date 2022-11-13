package com.plutoisnotaplanet.currencyconverterapp.application.domain.repository

import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.Currency
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    fun observeCurrencies(): Flow<List<Currency>>

    suspend fun uploadCurrencies()

    suspend fun changeCurrencyFavoriteState(currency: Currency)
}
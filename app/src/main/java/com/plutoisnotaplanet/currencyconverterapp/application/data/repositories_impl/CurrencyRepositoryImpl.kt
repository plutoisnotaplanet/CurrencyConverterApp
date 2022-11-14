package com.plutoisnotaplanet.currencyconverterapp.application.data.repositories_impl

import com.plutoisnotaplanet.currencyconverterapp.application.data.db.dao.CurrencyDao
import com.plutoisnotaplanet.currencyconverterapp.application.data.rest.Api
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.Currency
import com.plutoisnotaplanet.currencyconverterapp.application.domain.repository.CurrencyRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val api: Api,
    private val currencyDao: CurrencyDao
) : CurrencyRepository {


    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeCurrencies(): Flow<List<Currency>> {
        return currencyDao.getAllCurrenciesFlow().flatMapLatest { list ->
            if (list.isEmpty()) {
                updateCurrencies()
                flow { emit(list.map { it.toModel() }) }
            } else {
                flow { emit(list.map { it.toModel() }) }
            }
        }
    }

    override suspend fun changeCurrencyFavoriteState(currency: Currency) {
        currencyDao.updateFavorState(currency.name, !currency.isFavorite)
    }

    override suspend fun updateCurrencies() {
        val responseList =
            api.getExchangeRates().currencyRatesResponse.currenciesList

        currencyDao.save(responseList)
    }
}
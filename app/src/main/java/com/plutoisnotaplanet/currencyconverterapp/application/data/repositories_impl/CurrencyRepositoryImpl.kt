package com.plutoisnotaplanet.currencyconverterapp.application.data.repositories_impl

import com.plutoisnotaplanet.currencyconverterapp.application.data.db.dao.CurrencyDao
import com.plutoisnotaplanet.currencyconverterapp.application.data.rest.Api
import com.plutoisnotaplanet.currencyconverterapp.application.data.rest.ApiException
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.Currency
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.runResulting
import com.plutoisnotaplanet.currencyconverterapp.application.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val api: Api,
    private val currencyDao: CurrencyDao
): CurrencyRepository {


    override fun observeCurrencies(): Flow<List<Currency>> {
        return currencyDao.getAllCurrencies().map { list ->
            var currencies = list.map { it.toModel() }
            if (list.isEmpty()) {
                val response = api.getExchangeRates()
                val currenciesList = response.currencyRatesResponse.currenciesList
                currencyDao.save(currenciesList)

                currencies = currenciesList.map { it.toModel() }
                currencies
            } else {
                currencies
            }
        }
    }

    override suspend fun changeCurrencyFavoriteState(currency: Currency) {
        currencyDao.changeFavoriteState(currency.name, !currency.isFavorite)
    }

    override suspend fun uploadCurrencies() {
        val response = api.getExchangeRates()
        currencyDao.save(response.currencyRatesResponse.currenciesList)
    }


}
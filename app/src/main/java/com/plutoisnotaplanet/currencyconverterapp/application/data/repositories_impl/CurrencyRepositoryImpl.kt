package com.plutoisnotaplanet.currencyconverterapp.application.data.repositories_impl

import com.plutoisnotaplanet.currencyconverterapp.application.data.db.dao.CurrencyDao
import com.plutoisnotaplanet.currencyconverterapp.application.data.rest.Api
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.Currency
import com.plutoisnotaplanet.currencyconverterapp.application.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val api: Api,
    private val currencyDao: CurrencyDao
) : CurrencyRepository {

    override fun observeCurrencies(): Flow<List<Currency>> {
        return currencyDao.getAllCurrenciesFlow().map { list ->
            var resultList = list.map { it.toModel() }
            if (list.isEmpty()) {
                val exchangeRatesResponse =
                    api.getExchangeRates().currencyRatesResponse.currenciesList
                val countriesNamesResponse =
                    api.getCountriesNames().currenciesMap

                countriesNamesResponse.forEach { (currencyCode, countryName) ->
                    exchangeRatesResponse.find { it.currencyCode == currencyCode }?.let {
                        it.countryName = countryName
                    }
                }

                currencyDao.save(exchangeRatesResponse)
                resultList = exchangeRatesResponse.map { it.toModel() }
                resultList
            } else
                resultList

        }
    }

    override suspend fun changeCurrencyFavoriteState(currencyCode: String, isFavorite: Boolean) {
        currencyDao.updateFavorState(currencyCode, !isFavorite)
    }

    override suspend fun updateCurrencies() {
        val responseList =
            api.getExchangeRates().currencyRatesResponse.currenciesList

        currencyDao.save(responseList)
    }

    override suspend fun uploadCurrenciesCountriesNames() {
        val isCountriesNamesEmpty = currencyDao.getAllCurrencies().any { it.countryName.isNotBlank() }
        if (isCountriesNamesEmpty) return

        val responseList =
            api.getCountriesNames().currenciesMap
        currencyDao.setCountriesNames(responseList)
    }
}
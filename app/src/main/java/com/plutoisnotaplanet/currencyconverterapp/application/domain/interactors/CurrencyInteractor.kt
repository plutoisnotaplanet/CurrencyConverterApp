package com.plutoisnotaplanet.currencyconverterapp.application.domain.interactors

import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.*
import com.plutoisnotaplanet.currencyconverterapp.application.domain.repository.CurrencyRepository
import com.plutoisnotaplanet.currencyconverterapp.application.domain.repository.CurrencySortSettingsRepository
import com.plutoisnotaplanet.currencyconverterapp.application.domain.usecases.CurrencyUseCase
import com.plutoisnotaplanet.currencyconverterapp.application.utils.CurrencyConversion
import com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.currency_list.CurrencyScreenUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import java.math.BigDecimal
import javax.inject.Inject

class CurrencyInteractor @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val currencySortSettingsRepository: CurrencySortSettingsRepository
) : CurrencyUseCase {

    override suspend fun getSortedCurrenciesByListType(
        listType: CurrencyListType,
        queryText: String
    ): Flow<CurrencyScreenUiState> {
        return currencyRepository.observeCurrencies()
            .combine(currencySortSettingsRepository.observeSortSettings()) { currencies, pairOfSettings ->

                val (popularSortSettings, favoriteSortSettings) = pairOfSettings

                val activeSortSettings =
                    if (listType == CurrencyListType.POPULAR) popularSortSettings else favoriteSortSettings

                var resultList = currencies

                if (activeSortSettings.isCurrencyNotDefault) {
                    resultList = resultList.map { currency ->
                        currency.copy(
                            rate = CurrencyConversion.convertCurrency(
                                fromRate = BigDecimal(activeSortSettings.selectedCurrency.rate),
                                toRate = currency.rate
                            )
                        )
                    }
                }

                if (activeSortSettings.isSortActive) {
                    resultList = prepareSortedCurrenciesList(resultList, activeSortSettings)
                }

                if (listType.isFavorite) {
                    resultList = resultList.filter { it.isFavorite }
                }

                if (queryText.isNotBlank()) {
                    resultList = resultList.filter {
                        it.code.lowercase().contains(queryText, true) ||
                                it.countryName.lowercase().contains(queryText, true)
                    }
                }

                val finalList = resultList.map { it.toCurrencyViewItem(activeSortSettings.selectedCurrency.code) }

                if (listType.isPopular) {
                    CurrencyScreenUiState.Success(
                        sortSettings = popularSortSettings,
                        currenciesList = finalList
                    ).takeIf { resultList.isNotEmpty() } ?: CurrencyScreenUiState.Error("No currencies found")
                } else {
                    CurrencyScreenUiState.Success(
                        sortSettings = favoriteSortSettings,
                        currenciesList = finalList
                    ).takeIf { resultList.isNotEmpty() } ?: CurrencyScreenUiState.Error("Favorites is not found")
                }
            }.flowOn(Dispatchers.IO)
    }


    override suspend fun updateCurrencies(): Response<Unit> {
        return runResulting {
            currencyRepository.updateCurrencies()
        }
    }

    override suspend fun changeFavoriteCurrencyState(currency: CurrencyViewItem): Response<Unit> {
        return runResulting {
            currencyRepository.changeCurrencyFavoriteState(currency.code, currency.isFavorite)
        }
    }

    override fun prepareSortedCurrenciesList(
        currencies: List<Currency>,
        sortSettings: SortSettings
    ): List<Currency> {
        return when {
            sortSettings.sortByName.isAscending && sortSettings.sortByRate.isAscending -> {
                currencies.groupBy { it.countryName.first() }.map { it.value.sortedBy { it.rate } }.flatten()
            }
            sortSettings.sortByName.isAscending && sortSettings.sortByRate.isDescending -> {
                currencies.groupBy { it.countryName.first() }.map { it.value.sortedByDescending { it.rate } }
                    .flatten()
            }
            sortSettings.sortByName.isAscending && sortSettings.sortByRate.isDescending -> {
                currencies.groupBy { it.countryName.first() }.map { it.value.sortedBy { it.rate } }.reversed()
                    .flatten()
            }
            sortSettings.sortByName.isDescending && sortSettings.sortByRate.isDescending -> {
                currencies.groupBy { it.countryName.first() }.map { it.value.sortedByDescending { it.rate } }
                    .reversed().flatten()
            }
            sortSettings.sortByName.isAscending -> {
                currencies.sortedBy { it.countryName }
            }
            sortSettings.sortByName.isDescending -> {
                currencies.sortedByDescending { it.countryName }
            }
            sortSettings.sortByRate.isAscending -> {
                currencies.sortedBy { it.rate }
            }
            sortSettings.sortByRate.isDescending -> {
                currencies.sortedByDescending { it.rate }
            }
            else -> currencies.sortedBy { it.countryName }
        }
    }
}
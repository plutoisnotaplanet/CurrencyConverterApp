package com.plutoisnotaplanet.currencyconverterapp.application.domain.interactors

import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.*
import com.plutoisnotaplanet.currencyconverterapp.application.domain.repository.CurrencyRepository
import com.plutoisnotaplanet.currencyconverterapp.application.domain.repository.CurrencySortSettingsRepository
import com.plutoisnotaplanet.currencyconverterapp.application.domain.usecases.CurrencyUseCase
import com.plutoisnotaplanet.currencyconverterapp.application.utils.CurrencyConversion
import com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.currency_list.CurrencyScreenUiState
import com.plutoisnotaplanet.currencyconverterapp.ui.main.MainUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.math.BigDecimal
import javax.inject.Inject

class CurrencyInteractor @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val currencySortSettingsRepository: CurrencySortSettingsRepository
) : CurrencyUseCase {

    override suspend fun getSortedCurrenciesByListType(
        listType: CurrencyListType,
        queryText: String
    ): Flow<MainUiState> {
        return currencyRepository.observeCurrencies()
            .combine(currencySortSettingsRepository.observeSortSettings()) { currencies, pairOfSettings ->

                val (popularSortSettings, favoriteSortSettings) = pairOfSettings

                val activeSortSettings =
                    if (listType == CurrencyListType.POPULAR) popularSortSettings else favoriteSortSettings
                var resultList = currencies

                if (activeSortSettings.isCountrySelected) {
                    resultList = resultList.map { currency ->
                        Currency(
                            name = currency.name,
                            rate = CurrencyConversion.convertCurrency(
                                amount = BigDecimal.ONE,
                                fromRate = activeSortSettings.selectedCurrency.rate,
                                toRate = currency.rate
                            ),
                            isFavorite = currency.isFavorite
                        )
                    }
                }

                if (activeSortSettings.isSortActive) {
                    when {
                        activeSortSettings.isSortByNameActive && activeSortSettings.isSortByRateActive -> {
                            resultList =
                                prepareBothActiveSortSettings(resultList, activeSortSettings)
                        }
                        activeSortSettings.isSortByNameActive -> {
                            resultList = when (activeSortSettings.sortByName) {
                                SortBy.Ascending -> resultList.sortedBy { it.name }
                                SortBy.Descending -> resultList.sortedByDescending { it.name }
                                SortBy.None -> resultList
                            }
                        }
                        activeSortSettings.isSortByRateActive -> {
                            resultList = when (activeSortSettings.sortByRate) {
                                SortBy.Ascending -> resultList.sortedBy { it.rate }
                                SortBy.Descending -> resultList.sortedByDescending { it.rate }
                                SortBy.None -> resultList
                            }
                        }
                    }
                }

                var popularList = resultList
                var favoriteList = resultList.filter { it.isFavorite }

                if (queryText.isNotBlank()) {
                    when (listType) {
                        CurrencyListType.POPULAR -> {
                            popularList =
                                popularList.filter { it.name.lowercase().contains(queryText, true) }
                        }
                        CurrencyListType.FAVORITE -> {
                            favoriteList = favoriteList.filter {
                                it.name.lowercase().contains(queryText, true)
                            }
                        }
                    }
                }

                val popularUiState = CurrencyScreenUiState.Success(
                    sortSettings = popularSortSettings,
                    currenciesList = popularList
                ).takeIf { popularList.isNotEmpty() } ?: CurrencyScreenUiState.Error("No currencies found")

                val favoriteUiState = CurrencyScreenUiState.Success(
                    sortSettings = favoriteSortSettings,
                    currenciesList = favoriteList
                ).takeIf { favoriteList.isNotEmpty() } ?: CurrencyScreenUiState.Error("Favorites is not found")

                MainUiState(
                    popularUiState = popularUiState,
                    favoriteUiState = favoriteUiState
                )
            }.flowOn(Dispatchers.IO)
            .distinctUntilChanged { old, new ->
                if (listType == CurrencyListType.POPULAR) {
                    old.popularUiState == new.popularUiState
                } else {
                    old.favoriteUiState == new.favoriteUiState
                }
            }
    }


    override suspend fun updateCurrencies(): Response<Unit> {
        return runResulting {
            currencyRepository.uploadCurrencies()
        }
    }

    override suspend fun changeFavoriteCurrencyState(currency: Currency): Response<Unit> {
        return runResulting {
            currencyRepository.changeCurrencyFavoriteState(currency)
        }
    }

    private fun prepareBothActiveSortSettings(
        list: List<Currency>,
        sortSettings: SortSettings
    ): List<Currency> {
        return when {
            sortSettings.sortByName == SortBy.Ascending && sortSettings.sortByRate == SortBy.Ascending -> {
                list.groupBy { it.name.first() }.map { it.value.sortedBy { it.rate } }.flatten()
            }
            sortSettings.sortByName == SortBy.Ascending && sortSettings.sortByRate == SortBy.Descending -> {
                list.groupBy { it.name.first() }.map { it.value.sortedByDescending { it.rate } }
                    .flatten()
            }
            sortSettings.sortByName == SortBy.Descending && sortSettings.sortByRate == SortBy.Ascending -> {
                list.groupBy { it.name.first() }.map { it.value.sortedBy { it.rate } }.reversed()
                    .flatten()
            }
            else -> {
                list.groupBy { it.name.first() }.map { it.value.sortedByDescending { it.rate } }
                    .reversed().flatten()
            }
        }
    }
}
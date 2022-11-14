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
    ): Flow<CurrencyScreenUiState> {
        return currencyRepository.observeCurrencies()
            .combine(currencySortSettingsRepository.observeSortSettings()) { currencies, pairOfSettings ->

                Timber.e("emitInteractror")

                val (popularSortSettings, favoriteSortSettings) = pairOfSettings

                val activeSortSettings =
                    if (listType == CurrencyListType.POPULAR) popularSortSettings else favoriteSortSettings

                var resultList = currencies

                if (activeSortSettings.isCurrencyNotDefault) {
                    Timber.d("Sorting by ${activeSortSettings.selectedCurrency}")
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
                    resultList = prepareSortedCurrenciesList(resultList, activeSortSettings)
                }

                if (listType.isFavorite) {
                    resultList = resultList.filter { it.isFavorite }
                }

                if (queryText.isNotBlank()) {
                    resultList = resultList.filter {
                        it.name.lowercase().contains(queryText, true)
                    }
                }

                if (listType.isPopular) {
                    CurrencyScreenUiState.Success(
                        sortSettings = popularSortSettings,
                        currenciesList = resultList
                    ).takeIf { resultList.isNotEmpty() } ?: CurrencyScreenUiState.Error("No currencies found")
                } else {
                    CurrencyScreenUiState.Success(
                        sortSettings = favoriteSortSettings,
                        currenciesList = resultList
                    ).takeIf { resultList.isNotEmpty() } ?: CurrencyScreenUiState.Error("Favorites is not found")
                }
            }.flowOn(Dispatchers.IO)
    }


    override suspend fun updateCurrencies(): Response<Unit> {
        return runResulting {
            currencyRepository.updateCurrencies()
        }
    }

    override suspend fun changeFavoriteCurrencyState(currency: Currency): Response<Unit> {
        return runResulting {
            currencyRepository.changeCurrencyFavoriteState(currency)
        }
    }

    override fun prepareSortedCurrenciesList(
        currencies: List<Currency>,
        sortSettings: SortSettings
    ): List<Currency> {
        return when {
            sortSettings.sortByName.isAscending && sortSettings.sortByRate.isAscending -> {
                currencies.groupBy { it.name.first() }.map { it.value.sortedBy { it.rate } }.flatten()
            }
            sortSettings.sortByName.isAscending && sortSettings.sortByRate.isDescending -> {
                currencies.groupBy { it.name.first() }.map { it.value.sortedByDescending { it.rate } }
                    .flatten()
            }
            sortSettings.sortByName.isAscending && sortSettings.sortByRate.isDescending -> {
                currencies.groupBy { it.name.first() }.map { it.value.sortedBy { it.rate } }.reversed()
                    .flatten()
            }
            sortSettings.sortByName.isDescending && sortSettings.sortByRate.isDescending -> {
                currencies.groupBy { it.name.first() }.map { it.value.sortedByDescending { it.rate } }
                    .reversed().flatten()
            }
            sortSettings.sortByName.isAscending -> {
                currencies.sortedBy { it.name }
            }
            sortSettings.sortByName.isDescending -> {
                currencies.sortedByDescending { it.name }
            }
            sortSettings.sortByRate.isAscending -> {
                currencies.sortedBy { it.rate }
            }
            sortSettings.sortByRate.isDescending -> {
                currencies.sortedByDescending { it.rate }
            }
            else -> currencies
        }
    }
}
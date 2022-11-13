package com.plutoisnotaplanet.currencyconverterapp.ui.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.*
import com.plutoisnotaplanet.currencyconverterapp.application.domain.usecases.CurrencySortSettingsUseCase
import com.plutoisnotaplanet.currencyconverterapp.application.domain.usecases.CurrencyUseCase
import com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.currency_list.CurrencyScreenAction
import com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.currency_list.CurrencyScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val currencyUseCase: CurrencyUseCase,
    private val currencySortSettingsUseCase: CurrencySortSettingsUseCase
) : ViewModel() {

    private val _popularUiState: MutableState<CurrencyScreenUiState> = mutableStateOf(CurrencyScreenUiState.Loading)
    private val _favoriteUiState: MutableState<CurrencyScreenUiState> = mutableStateOf(CurrencyScreenUiState.Loading)

    private val popularQuerySearchText = MutableStateFlow("")
    private val favoriteQuerySearchText = MutableStateFlow("")

    val popularUiState: State<CurrencyScreenUiState> = _popularUiState
    val favoriteUiState: State<CurrencyScreenUiState> = _favoriteUiState

    fun handleAction(action: CurrencyScreenAction) {
        when (action) {
            is CurrencyScreenAction.SelectCurrency -> {
                setSelectedCurrency(action.listType, action.currency)
            }
            is CurrencyScreenAction.ToggleFavorite -> {
                changeFavoriteCurrencyState(action.currency)
            }
            is CurrencyScreenAction.ChangeSortSettings -> {
                updateSortSettings(action.listType, action.sortSettings)
            }
            is CurrencyScreenAction.FilterCurrenciesByQuery -> {
                when (action.listType) {
                    CurrencyListType.POPULAR -> popularQuerySearchText.update { action.query }
                    CurrencyListType.FAVORITE -> favoriteQuerySearchText.update { action.query }
                }
            }
            is CurrencyScreenAction.ObserveCurrenciesByListType -> {
                observeUiStateByListType(action.listType)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeUiStateByListType(listType: CurrencyListType) {
        val currentQueryFlow =
            if (listType == CurrencyListType.POPULAR) popularQuerySearchText else favoriteQuerySearchText

        viewModelScope.launch(Dispatchers.IO) {
            currentQueryFlow.flatMapLatest { query ->
                currencyUseCase.getSortedCurrenciesByListType(listType, query)
            }
                .distinctUntilChanged()
                .catch { Timber.e(it) }
                .collectLatest { uiState ->
                    when (listType) {
                        CurrencyListType.POPULAR -> _popularUiState.value = uiState.popularUiState
                        CurrencyListType.FAVORITE -> _favoriteUiState.value =
                            uiState.favoriteUiState
                    }
                }
        }
    }

    private fun updateSortSettings(listType: CurrencyListType, sortSettings: SortSettings) {
        viewModelScope.launch(Dispatchers.IO) {
            currencySortSettingsUseCase.updateSortSettings(listType, sortSettings)
                .onSuccess { }
                .onFailure { Timber.e(it) }
        }
    }

    fun updateCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            currencyUseCase.updateCurrencies()
                .onSuccess { Timber.e("success updated") }
                .onFailure { Timber.e("failure updated") }
        }
    }

    private fun setSelectedCurrency(listType: CurrencyListType, currency: Currency) {
        viewModelScope.launch(Dispatchers.IO) {
            currencySortSettingsUseCase.updateSelectedCurrency(listType, currency)
                .onSuccess { Timber.e("success selected ${currency.name}") }
                .onFailure { Timber.e(it.message) }
        }
    }

    private fun changeFavoriteCurrencyState(currency: Currency) {
        viewModelScope.launch(Dispatchers.IO) {
            currencyUseCase.changeFavoriteCurrencyState(currency)
                .onSuccess { Timber.e("success updated") }
                .onFailure { Timber.e("failure updated") }
        }
    }
}
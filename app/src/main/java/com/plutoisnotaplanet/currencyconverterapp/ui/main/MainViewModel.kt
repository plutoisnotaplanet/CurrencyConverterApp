package com.plutoisnotaplanet.currencyconverterapp.ui.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plutoisnotaplanet.currencyconverterapp.R
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.*
import com.plutoisnotaplanet.currencyconverterapp.application.domain.usecases.CurrencySortSettingsUseCase
import com.plutoisnotaplanet.currencyconverterapp.application.domain.usecases.CurrencyUseCase
import com.plutoisnotaplanet.currencyconverterapp.application.extensions.launchOnIo
import com.plutoisnotaplanet.currencyconverterapp.ui.common.SnackbarState
import com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.currency_list.CurrencyScreenAction
import com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.currency_list.CurrencyScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val currencyUseCase: CurrencyUseCase,
    private val currencySortSettingsUseCase: CurrencySortSettingsUseCase
) : ViewModel() {

    private val _popularUiState: MutableState<CurrencyScreenUiState> =
        mutableStateOf(CurrencyScreenUiState.Loading)
    private val _favoriteUiState: MutableState<CurrencyScreenUiState> =
        mutableStateOf(CurrencyScreenUiState.Loading)

    private val popularQuerySearchText = MutableStateFlow("")
    private val favoriteQuerySearchText = MutableStateFlow("")

    private val currentUiState: (CurrencyListType) -> MutableState<CurrencyScreenUiState>
        inline get() = {
            when (it) {
                CurrencyListType.POPULAR -> _popularUiState
                CurrencyListType.FAVORITE -> _favoriteUiState
            }
        }

    private val currentQueryFlow: (CurrencyListType) -> Flow<String>
        inline get() = { listType ->
            when (listType) {
                CurrencyListType.POPULAR -> popularQuerySearchText
                CurrencyListType.FAVORITE -> favoriteQuerySearchText
            }
        }

    private val _snackbarFlow = MutableSharedFlow<SnackbarState>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val snackbarFlow = _snackbarFlow.asSharedFlow()

    val popularUiState: State<CurrencyScreenUiState> = _popularUiState
    val favoriteUiState: State<CurrencyScreenUiState> = _favoriteUiState

    fun obtainAction(action: CurrencyScreenAction) {
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
            is CurrencyScreenAction.UpdateDataRemote -> {
                updateCurrenciesRemote(action.listType)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeUiStateByListType(listType: CurrencyListType) {
        viewModelScope.launchOnIo {
            currentQueryFlow(listType).flatMapLatest { query ->
                currencyUseCase.getSortedCurrenciesByListType(listType, query)
            }
                .catch { showSnackbar(it.message) }
                .collectLatest { uiState ->
                    when (listType) {
                        CurrencyListType.POPULAR -> _popularUiState.value = uiState
                        CurrencyListType.FAVORITE -> _favoriteUiState.value = uiState
                    }
                }
        }
    }

    private fun updateSortSettings(listType: CurrencyListType, sortSettings: SortSettings) {
        viewModelScope.launchOnIo {
            currencySortSettingsUseCase.updateSortSettings(listType, sortSettings)
                .onFailure { showSnackbar(resMessage = R.string.error_on_update_sort_settings) }
        }
    }

    private fun updateCurrenciesRemote(listType: CurrencyListType) {
        currentUiState(listType).value = CurrencyScreenUiState.Loading
        viewModelScope.launchOnIo {
            currencyUseCase.updateCurrencies()
                .onFailure {
                    showSnackbar(resMessage = R.string.error_on_update)
                    observeUiStateByListType(listType)
                }
        }
    }

    private fun setSelectedCurrency(listType: CurrencyListType, currency: Currency) {
        viewModelScope.launchOnIo {
            currencySortSettingsUseCase.updateSelectedCurrency(listType, currency)
                .onFailure { showSnackbar(resMessage = R.string.error_on_select_base_currency) }
        }
    }

    private fun changeFavoriteCurrencyState(currency: Currency) {
        viewModelScope.launchOnIo {
            currencyUseCase.changeFavoriteCurrencyState(currency)
                .onFailure { showSnackbar(resMessage = R.string.error_on_select_favorite_currency) }
        }
    }

    private suspend fun showSnackbar(stringMessage: String? = null, resMessage: Int? = null) {
        if (stringMessage != null) {
            _snackbarFlow.emit(SnackbarState.StringMessage(stringMessage))
        } else if (resMessage != null) {
            _snackbarFlow.emit(SnackbarState.ResMessage(resMessage))
        }
    }
}
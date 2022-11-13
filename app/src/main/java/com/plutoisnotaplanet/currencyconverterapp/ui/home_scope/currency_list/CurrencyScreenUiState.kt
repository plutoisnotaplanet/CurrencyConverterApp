package com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.currency_list

import androidx.compose.runtime.Stable
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.Currency
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.SortBy
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.SortSettings

@Stable
sealed class CurrencyScreenUiState {

    object Loading : CurrencyScreenUiState()

    data class Error(val message: String) : CurrencyScreenUiState()

    data class Success(
        val sortSettings: SortSettings,
        val currenciesList: List<Currency>
    ) : CurrencyScreenUiState()

    fun getSortSettingsIfExist(): SortSettings {
        return when (this) {
            is Success -> sortSettings
            else -> SortSettings(sortByName = SortBy.None, sortByRate = SortBy.None, selectedCurrency = Currency.getUsdCurrency())
        }
    }
}

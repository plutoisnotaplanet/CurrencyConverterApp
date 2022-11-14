package com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.currency_list

import androidx.compose.runtime.Stable
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.CurrencyViewItem
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.SortBy
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.SortSettings

@Stable
sealed class CurrencyScreenUiState {

    object Loading : CurrencyScreenUiState()

    data class Error(val message: String) : CurrencyScreenUiState()

    data class Success(
        val sortSettings: SortSettings,
        val currenciesList: List<CurrencyViewItem>
    ) : CurrencyScreenUiState()

    fun getSortSettingsIfExist(): SortSettings {
        return when (this) {
            is Success -> sortSettings
            else -> SortSettings(
                sortByName = SortBy.None,
                sortByRate = SortBy.None,
                selectedCurrency = CurrencyViewItem.getEmpty()
            )
        }
    }

    val isSuccess: Boolean
        get() = this is Success

    val isLoading: Boolean
        get() = this is Loading

}

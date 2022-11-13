package com.plutoisnotaplanet.currencyconverterapp.ui.main

import androidx.compose.runtime.Stable
import com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.currency_list.CurrencyScreenUiState

@Stable
data class MainUiState(
    val popularUiState: CurrencyScreenUiState,
    val favoriteUiState: CurrencyScreenUiState
) {
}
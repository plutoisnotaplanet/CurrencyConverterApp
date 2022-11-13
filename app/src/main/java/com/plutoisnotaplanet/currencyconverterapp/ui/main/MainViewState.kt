package com.plutoisnotaplanet.currencyconverterapp.ui.main

import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.Currency

sealed class MainViewState {

    object Loading : MainViewState()

    data class Success(val data: List<Currency>) : MainViewState()

    data class Error(val error: Throwable) : MainViewState()
}


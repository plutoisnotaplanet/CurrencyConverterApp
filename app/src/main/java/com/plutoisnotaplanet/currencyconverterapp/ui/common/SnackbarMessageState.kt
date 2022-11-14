package com.plutoisnotaplanet.currencyconverterapp.ui.common

import androidx.annotation.StringRes

sealed class SnackbarMessageState {

    data class ResMessage(@StringRes val message: Int) : SnackbarMessageState()

    data class StringMessage(val message: String) : SnackbarMessageState()
}
package com.plutoisnotaplanet.currencyconverterapp.ui.common

import androidx.annotation.StringRes

sealed class SnackbarState {

    data class ResMessage(@StringRes val message: Int) : SnackbarState()

    data class StringMessage(val message: String) : SnackbarState()
}
package com.plutoisnotaplanet.currencyconverterapp.application.domain.model

data class CurrencyViewItem(
    val code: String,
    val countryName: String,
    val rate: String,
    val isFavorite: Boolean
) {

    companion object {
        fun getEmpty(): CurrencyViewItem = CurrencyViewItem(code = "USD", countryName = "United States of America", rate = "1.0", isFavorite = false)
    }

    val isNotDefault: Boolean
        get() = code != "USD"
}
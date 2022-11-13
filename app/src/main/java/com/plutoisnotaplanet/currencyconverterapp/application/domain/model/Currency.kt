package com.plutoisnotaplanet.currencyconverterapp.application.domain.model


data class Currency(
    val name: String,
    val rate: Double,
    val isFavorite: Boolean
) {
    companion object {
        fun getUsdCurrency(): Currency = Currency("USD", 1.0, false)
    }

    val isNotDefault: Boolean
        get() = this != getUsdCurrency()

}
package com.plutoisnotaplanet.currencyconverterapp.application.domain.model

data class CurrencyViewItem(
    val name: String,
    val rate: String,
    val isFavorite: Boolean
) {

}
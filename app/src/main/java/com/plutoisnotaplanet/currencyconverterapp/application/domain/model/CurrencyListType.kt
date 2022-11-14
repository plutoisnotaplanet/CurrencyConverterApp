package com.plutoisnotaplanet.currencyconverterapp.application.domain.model

import javax.annotation.concurrent.Immutable

@Immutable
enum class CurrencyListType {
    POPULAR, FAVORITE;

    val isPopular: Boolean
        get() = this == POPULAR

    val isFavorite: Boolean
        get() = this == FAVORITE
}
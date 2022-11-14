package com.plutoisnotaplanet.currencyconverterapp.application.domain.model

import javax.annotation.concurrent.Immutable

@Immutable
enum class SortBy(val viewValue: String) {
    None(""), Ascending("По возрастанию"), Descending("По убыванию");

    val isAscending: Boolean
        get() = this == Ascending

    val isDescending: Boolean
        get() = this == Descending
}
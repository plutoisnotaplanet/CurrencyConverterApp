package com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.currency_list

import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.CurrencyListType
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.CurrencyViewItem
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.SortSettings

sealed class CurrencyScreenAction {

    data class SelectCurrency(val listType: CurrencyListType, val currency: CurrencyViewItem) : CurrencyScreenAction()

    data class ToggleFavorite(val currency: CurrencyViewItem) : CurrencyScreenAction()

    data class ChangeSortSettings(val listType: CurrencyListType, val sortSettings: SortSettings) : CurrencyScreenAction()

    data class FilterCurrenciesByQuery(val listType: CurrencyListType, val query: String) : CurrencyScreenAction()

    data class ObserveCurrenciesByListType(val listType: CurrencyListType) : CurrencyScreenAction()

    data class UpdateDataRemote(val listType: CurrencyListType): CurrencyScreenAction()
}
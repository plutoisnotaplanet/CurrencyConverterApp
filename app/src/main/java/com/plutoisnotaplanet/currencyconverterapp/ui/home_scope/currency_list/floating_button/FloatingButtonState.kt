package com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.currency_list.floating_button

enum class FloatingButtonState {
    Collapsed,
    Expanded,
    SearchView;

    val getPrevious: FloatingButtonState
        get() = when (this) {
            Collapsed -> Collapsed
            Expanded -> Collapsed
            SearchView -> Expanded
        }

    val getNext: FloatingButtonState
        get() = when (this) {
            Collapsed -> Expanded
            Expanded -> SearchView
            SearchView -> SearchView
        }

    val isExpanded: Boolean
        get() = this == Expanded

    val isSearchView: Boolean
        get() = this == SearchView

    val isCollapsed: Boolean
        get() = this == Collapsed
}
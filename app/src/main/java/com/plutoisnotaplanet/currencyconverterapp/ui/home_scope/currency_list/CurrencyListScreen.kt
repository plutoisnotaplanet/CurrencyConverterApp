package com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.currency_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.Currency
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.CurrencyListType
import com.plutoisnotaplanet.currencyconverterapp.application.utils.OnLifecycleEvent
import com.plutoisnotaplanet.currencyconverterapp.ui.components.DefaultTitle
import com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.currency_list.floating_button.CurrencyFloatingActionButtons
import com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.currency_list.floating_button.FloatingButtonState
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CurrencyListScreen(
    modifier: Modifier = Modifier,
    uiState: CurrencyScreenUiState,
    listType: CurrencyListType,
    lazyListState: LazyListState = LazyListState(),
    showSortSettingsScreen: () -> Unit,
    obtainAction: (CurrencyScreenAction) -> Unit = {}
) {

    var buttonState by remember {
        mutableStateOf(FloatingButtonState.Collapsed)
    }

    val coroutineScope = rememberCoroutineScope()

    OnLifecycleEvent { owner, event ->
        if (event == Lifecycle.Event.ON_RESUME) {
            obtainAction(CurrencyScreenAction.ObserveCurrenciesByListType(listType))
        }
    }

    Scaffold(
        floatingActionButtonPosition = if (buttonState == FloatingButtonState.SearchView) {
            FabPosition.Center
        } else {
            FabPosition.End
        },
        floatingActionButton = {
            CurrencyFloatingActionButtons(
                selectedCurrency = if (uiState is CurrencyScreenUiState.Success) uiState.sortSettings.selectedCurrency else Currency.getUsdCurrency(),
                onSortSettingsAction = showSortSettingsScreen,
                state = buttonState,
                onChangeButtonState = { buttonState = it },
                onQueryResult = {
                    obtainAction(
                        CurrencyScreenAction.FilterCurrenciesByQuery(
                            listType = listType,
                            query = it
                        )
                    )
                },
                onScrollToSelectedCurrency = {
                    if (uiState is CurrencyScreenUiState.Success) {
                        val itemInList = uiState.currenciesList.indexOfFirst { currency ->
                            currency.name == it.name
                        }
                        if (itemInList != -1) {
                            coroutineScope.launch {
                                lazyListState.scrollToItem(itemInList)
                            }
                        }
                    }
                },
                onScrollToTopAction = {
                    coroutineScope.launch {
                        lazyListState.animateScrollToItem(0)
                    }
                }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            state = lazyListState
        ) {

            when (uiState) {
                is CurrencyScreenUiState.Loading -> {
                    ShowLoader(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
                is CurrencyScreenUiState.Error -> {
                    ShowError(errorMessage = uiState.message, modifier = Modifier.fillMaxSize())
                }
                is CurrencyScreenUiState.Success -> {
                    items(
                        items = uiState.currenciesList,
                        key = { item -> item.name },
                    ) { item ->

                        CurrencyListItem(
                            modifier = Modifier.animateItemPlacement(),
                            item = item,
                            currentListType = listType,
                            onAction = obtainAction,
                            selectedCurrencyName = uiState.sortSettings.selectedCurrency.name
                        )
                    }
                }
            }
        }
    }
}

fun LazyListScope.ShowLoader(
    modifier: Modifier = Modifier
) {
    item {
        Box(
            modifier = modifier
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
            )
        }
    }
}

fun LazyListScope.ShowError(
    modifier: Modifier = Modifier,
    errorMessage: String,
) {
    item {
        Box(
            modifier = modifier
        ) {
            DefaultTitle(
                modifier = Modifier
                    .align(Alignment.Center),
                value = errorMessage
            )
        }
    }
}


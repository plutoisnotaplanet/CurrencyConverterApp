package com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.currency_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.CurrencyListType
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.CurrencyViewItem
import com.plutoisnotaplanet.currencyconverterapp.application.utils.OnLifecycleEvent
import com.plutoisnotaplanet.currencyconverterapp.ui.components.DefaultTitle
import com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.currency_list.floating_button.CurrencyFloatingActionButtons
import com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.currency_list.floating_button.FloatingButtonState
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CurrencyListScreen(
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
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = uiState.isLoading)

    var lastSelectedCurrency by remember() {
        mutableStateOf(CurrencyViewItem.getEmpty())
    }

    LaunchedEffect(key1 = uiState) {
        if (uiState.isSuccess) {
            lastSelectedCurrency =
                (uiState as CurrencyScreenUiState.Success).sortSettings.selectedCurrency
        }
    }

    OnLifecycleEvent { owner, event ->
        if (event == Lifecycle.Event.ON_RESUME) {
            obtainAction(CurrencyScreenAction.ObserveCurrenciesByListType(listType))
        }
    }

    Scaffold(
        floatingActionButtonPosition = if (buttonState == FloatingButtonState.SearchView)
            FabPosition.Center
        else
            FabPosition.End,
        floatingActionButton = {
            CurrencyFloatingActionButtons(
                selectedCurrency = lastSelectedCurrency,
                onOpenSortSettings = showSortSettingsScreen,
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
                        val indexInList = uiState.currenciesList.indexOfFirst { currency ->
                            currency.code == it.code
                        }
                        if (indexInList != -1) {
                            coroutineScope.launch {
                                lazyListState.scrollToItem(indexInList)
                            }
                        }
                    }
                },
                onScrollToTopAction = {
                    coroutineScope.launch {
                        lazyListState.scrollToItem(0)
                    }
                }
            )
        }
    ) { paddingValues ->

        SwipeRefresh(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            state = swipeRefreshState,
            onRefresh = { obtainAction(CurrencyScreenAction.UpdateDataRemote(listType)) }) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = lazyListState
            ) {

                when (uiState) {
                    is CurrencyScreenUiState.Loading -> {
                        item {
                            ShowLoader(
                                modifier = Modifier.fillParentMaxSize()
                            )
                        }
                    }
                    is CurrencyScreenUiState.Error -> {
                        item {
                            ShowError(
                                errorMessage = uiState.message,
                                modifier = Modifier.fillParentMaxSize()
                            )
                        }
                    }
                    is CurrencyScreenUiState.Success -> {
                        items(
                            items = uiState.currenciesList,
                            key = { item -> item.code },
                        ) { item ->

                            CurrencyListItem(
                                modifier = Modifier.animateItemPlacement(),
                                item = item,
                                currentListType = listType,
                                onAction = obtainAction,
                                selectedCurrencyName = uiState.sortSettings.selectedCurrency.code
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShowLoader(
    modifier: Modifier = Modifier
) {
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

@Composable
fun ShowError(
    modifier: Modifier = Modifier,
    errorMessage: String,
) {
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


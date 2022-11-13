package com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.pager

import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.CurrencyListType
import com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.currency_list.CurrencyListScreen
import com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.currency_list.CurrencyScreenAction
import com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.currency_list.sort_screen.CurrencySortScreen
import com.plutoisnotaplanet.currencyconverterapp.ui.main.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun HorizontalPagerScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
    pagerState: PagerState
) {
    val popularLazyListState = rememberLazyListState()
    val favoriteLazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val popularUiState by viewModel.popularUiState
    val favoriteUiState by viewModel.favoriteUiState

    val currencySortScreenState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    val backDispatcher: OnBackPressedDispatcher =
        LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher

    BackHandler(enabled = currencySortScreenState.isVisible) {
        coroutineScope.launch {
            currencySortScreenState.hide()
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
    ) {

        HorizontalTabs(
            pagerState = pagerState,
            scope = coroutineScope
        )

        HorizontalPager(
            count = 2,
            state = pagerState,
            userScrollEnabled = !currencySortScreenState.isVisible,
            modifier = modifier.fillMaxSize()
        ) { currentPage ->

            CurrencyListScreen(
                listType = if (currentPage == 0) CurrencyListType.POPULAR else CurrencyListType.FAVORITE,
                lazyListState = if (currentPage == 0) popularLazyListState else favoriteLazyListState,
                uiState = if (currentPage == 0) popularUiState else favoriteUiState,
                obtainAction = viewModel::handleAction,
                showSortSettingsScreen = {
                    showOrHideSortSettingsScreen(
                        coroutineScope = coroutineScope,
                        currencySortScreenState = currencySortScreenState
                    )
                }
            )

            ModalBottomSheetLayout(
                sheetState = currencySortScreenState,
                sheetElevation = 4.dp,
                sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                sheetContent = {
                    CurrencySortScreen(
                        sortSettings = if (currentPage == 0) popularUiState.getSortSettingsIfExist() else favoriteUiState.getSortSettingsIfExist(),
                        updateSortSettings = { newSettings ->
                            viewModel.handleAction(
                                CurrencyScreenAction.ChangeSortSettings(
                                    listType = if (currentPage == 0) CurrencyListType.POPULAR else CurrencyListType.FAVORITE,
                                    sortSettings = newSettings
                                )
                            )
                        }
                    )
                }) {}

        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
fun showOrHideSortSettingsScreen(
    coroutineScope: CoroutineScope,
    currencySortScreenState: ModalBottomSheetState,
) {
    coroutineScope.launch {
        if (currencySortScreenState.isVisible) {
            currencySortScreenState.hide()
        } else {
            currencySortScreenState.show()
        }
    }
}
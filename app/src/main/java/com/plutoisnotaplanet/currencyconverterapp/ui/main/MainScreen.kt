package com.plutoisnotaplanet.currencyconverterapp.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.currency_list.CurrencyScreenAction
import com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.currency_list.sort_screen.CurrencySortScreen
import com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.pager.HorizontalPagerScreen

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val pagerState = rememberPagerState()

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Color.White
    ) { paddingValues ->

        HorizontalPagerScreen(
            modifier = Modifier.padding(paddingValues),
            viewModel = viewModel,
            pagerState = pagerState,
        )
    }
}
package com.plutoisnotaplanet.currencyconverterapp.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.plutoisnotaplanet.currencyconverterapp.ui.common.SnackbarController
import com.plutoisnotaplanet.currencyconverterapp.ui.common.SnackbarMessageState
import com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.pager.HorizontalPagerScreen
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val pagerState = rememberPagerState()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        val snackbarController = SnackbarController(this)

        viewModel.snackbarFlow.collectLatest { state ->
            when (state) {
                is SnackbarMessageState.ResMessage -> {
                    snackbarController.showSnackbar(
                        scaffoldState = scaffoldState,
                        message = context.getString(state.message),
                    )
                }
                is SnackbarMessageState.StringMessage -> {
                    snackbarController.showSnackbar(
                        scaffoldState = scaffoldState,
                        message = state.message,
                    )
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { SnackbarHost(hostState = scaffoldState.snackbarHostState) },
    ) { paddingValues ->
        HorizontalPagerScreen(
            modifier = Modifier.padding(paddingValues),
            viewModel = viewModel,
            pagerState = pagerState,
        )
    }
}
package com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.pager

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.plutoisnotaplanet.currencyconverterapp.R
import com.plutoisnotaplanet.currencyconverterapp.ui.components.DefaultTitle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Preview(showBackground = true)
@Composable
fun HorizontalTabs(
    modifier: Modifier = Modifier,
    items: List<HorizontalPagerContent> = listOf(
        HorizontalPagerContent(title = stringResource(id = R.string.tt_popular)),
        HorizontalPagerContent(title = stringResource(id = R.string.tt_favorite)),
    ),
    pagerState: PagerState = PagerState(0),
    scope: CoroutineScope = rememberCoroutineScope()
) {

    TabRow(
        modifier = modifier,
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier
                    .pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }
    ) {
        items.forEachIndexed { index, item ->
            Tab(
                modifier = Modifier.padding(bottom = 16.dp, top = 12.dp),
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(page = index)
                    }
                }) {
                DefaultTitle(value = item.title, textColor = Color.White)
            }
        }
    }
}
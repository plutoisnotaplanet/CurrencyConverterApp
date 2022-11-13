package com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.currency_list.floating_button

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.Currency
import com.plutoisnotaplanet.currencyconverterapp.application.utils.ResourceUtil
import com.plutoisnotaplanet.currencyconverterapp.ui.components.CurrencyFlagActionButton
import com.plutoisnotaplanet.currencyconverterapp.ui.components.DefaultTitle
import com.plutoisnotaplanet.currencyconverterapp.ui.components.IconifiedActionButton
import com.plutoisnotaplanet.currencyconverterapp.ui.components.MultifyActionButton
import com.plutoisnotaplanet.currencyconverterapp.ui.components.modifier.pushedAnimation
import com.plutoisnotaplanet.currencyconverterapp.ui.theme.light_secondary

@OptIn(
    ExperimentalAnimationApi::class
)
@Composable
fun CurrencyFloatingActionButtons(
    selectedCurrency: Currency = Currency.getUsdCurrency(),
    state: FloatingButtonState,
    onChangeButtonState: (FloatingButtonState) -> Unit,
    onQueryResult: (String) -> Unit,
    onSortSettingsAction: () -> Unit = {},
    onScrollToSelectedCurrency: (Currency) -> Unit,
    onScrollToTopAction: () -> Unit
) {

    val searchViewState by remember {
        mutableStateOf(SearchState())
    }

    searchViewState.onQueryResult(
        debounce = 300
    ) { query ->
        onQueryResult(query)
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        AnimatedVisibility(visible = !state.isSearchView) {
            CurrencyFlagActionButton(
                state = state,
                selectedCurrency = selectedCurrency,
                onScrollToSelectedCurrency = { onScrollToSelectedCurrency(selectedCurrency) },
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            MultifyActionButton(
                state = state,
                query = searchViewState.query,
                onQueryChange = { searchViewState.query = it },
                onSearchFocusChange = { searchViewState.focused = it },
                onClearQuery = {
                    searchViewState.query = TextFieldValue("")
                    onQueryResult("")
                },
                focused = searchViewState.focused,
                onChangeState = {
                    onChangeButtonState(it)
                }
            )
            AnimatedVisibility(visible = state.isExpanded) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconifiedActionButton(
                        onActionClick = onSortSettingsAction,
                        imageVector = Icons.Default.Sort
                    )
                    IconifiedActionButton(
                        onActionClick = onScrollToTopAction,
                        imageVector = Icons.Default.ArrowUpward
                    )
                }
            }
            AnimatedVisibility(visible = !state.isCollapsed) {
                IconifiedActionButton(
                    onActionClick = { onChangeButtonState(state.getPrevious) },
                    imageVector = Icons.Default.ArrowBack
                )
            }
        }
    }
}

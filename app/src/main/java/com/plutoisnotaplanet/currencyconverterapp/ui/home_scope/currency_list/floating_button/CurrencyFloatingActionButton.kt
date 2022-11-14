package com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.currency_list.floating_button

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.TextFieldValue
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.Currency
import com.plutoisnotaplanet.currencyconverterapp.ui.components.CurrencyFlagActionButton
import com.plutoisnotaplanet.currencyconverterapp.ui.components.IconifiedActionButton
import com.plutoisnotaplanet.currencyconverterapp.ui.components.MultifyActionButton
import com.plutoisnotaplanet.currencyconverterapp.ui.components.SearchState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@OptIn(
    ExperimentalAnimationApi::class, FlowPreview::class, ExperimentalCoroutinesApi::class
)
@Composable
fun CurrencyFloatingActionButtons(
    selectedCurrency: Currency = Currency.getUsdCurrency(),
    state: FloatingButtonState,
    onChangeButtonState: (FloatingButtonState) -> Unit,
    onQueryResult: (String) -> Unit,
    onOpenSortSettings: () -> Unit = {},
    onScrollToSelectedCurrency: (Currency) -> Unit,
    onScrollToTopAction: () -> Unit
) {

    val searchViewState by remember {
        mutableStateOf(SearchState())
    }

    LaunchedEffect(key1 = state.isSearchView) {
        snapshotFlow { searchViewState.query }
            .distinctUntilChanged()
            .filter { query: TextFieldValue ->
                query.text.isNotEmpty()
            }
            .debounce(300)
            .mapLatest { query: TextFieldValue ->
                val text = query.text.trim().lowercase()
                onQueryResult(text)
            }
            .collect()
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
                onChangeState = onChangeButtonState
            )
            AnimatedContent(targetState = state, transitionSpec = {
                fadeIn() + expandHorizontally() with fadeOut() + shrinkHorizontally()
            }) { animatedState ->
                when {
                    !animatedState.isCollapsed -> {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AnimatedVisibility(visible = animatedState.isExpanded) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconifiedActionButton(
                                        onActionClick = onOpenSortSettings,
                                        imageVector = Icons.Default.Sort
                                    )
                                    IconifiedActionButton(
                                        onActionClick = onScrollToTopAction,
                                        imageVector = Icons.Default.ArrowUpward
                                    )
                                }
                            }
                            IconifiedActionButton(
                                onActionClick = {
                                    onChangeButtonState(state.getPrevious)
                                    if (state.isSearchView) {
                                        searchViewState.clear()
                                        onQueryResult("")
                                    }
                                },
                                imageVector = Icons.Default.ArrowBack
                            )
                        }
                    }
                }
            }
        }
    }
}

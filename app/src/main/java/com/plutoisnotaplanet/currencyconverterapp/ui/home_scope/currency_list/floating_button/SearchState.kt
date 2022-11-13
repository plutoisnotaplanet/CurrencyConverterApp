package com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.currency_list.floating_button

import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

class SearchState {

    var focused by mutableStateOf(false)

    var query by mutableStateOf(TextFieldValue())

    fun clear() {
        query = TextFieldValue()
        focused = false
    }
}

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@Composable
fun SearchState.onQueryResult(
    debounce: Long,
    onQueryResult: (String) -> Unit
) =
    this.also { state ->
        LaunchedEffect(key1 = Unit) {
            snapshotFlow { state.query }
                .distinctUntilChanged()
                .filter { query: TextFieldValue ->
                    query.text.isNotEmpty()
                }
                .debounce(debounce)
                .mapLatest { query: TextFieldValue ->
                    val text = query.text.trim().lowercase()
                    onQueryResult(text)
                }
                .collect()
        }
    }
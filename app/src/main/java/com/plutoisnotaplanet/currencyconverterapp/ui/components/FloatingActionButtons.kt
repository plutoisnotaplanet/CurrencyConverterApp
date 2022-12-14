package com.plutoisnotaplanet.currencyconverterapp.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.CurrencyViewItem
import com.plutoisnotaplanet.currencyconverterapp.application.utils.ResourceUtil
import com.plutoisnotaplanet.currencyconverterapp.ui.components.modifier.pushedAnimation
import com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.currency_list.floating_button.FloatingButtonState


@Preview
@Composable
fun IconifiedActionButton(
    modifier: Modifier = Modifier.padding(start = 12.dp),
    imageVector: ImageVector = Icons.Default.Search,
    onActionClick: () -> Unit = {}
) {
    FloatingActionButton(
        modifier = modifier
            .pushedAnimation { onActionClick() },
        backgroundColor = MaterialTheme.colors.secondary,
        onClick = { },
    ) {
        Icon(
            imageVector = imageVector,
            tint = MaterialTheme.colors.onSecondary,
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
        )
    }
}

@Preview
@Composable
fun CurrencyFlagActionButton(
    modifier: Modifier = Modifier,
    state: FloatingButtonState = FloatingButtonState.Collapsed,
    onScrollToSelectedCurrency: () -> Unit = {},
    selectedCurrency: CurrencyViewItem = CurrencyViewItem.getEmpty(),
) {
    FloatingActionButton(
        modifier = Modifier.pushedAnimation { onScrollToSelectedCurrency() },
        backgroundColor = MaterialTheme.colors.secondary,
        onClick = { }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = ResourceUtil.painterResourceByName(name = "usd_${selectedCurrency.code}"),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .padding(horizontal = 12.dp)
            )

            AnimatedVisibility(visible = state == FloatingButtonState.Collapsed) {
                DefaultTitle(
                    value = selectedCurrency.code,
                    textColor = Color.White,
                    modifier = Modifier.padding(end = 12.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MultifyActionButton(
    state: FloatingButtonState = FloatingButtonState.SearchView,
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
    focused: Boolean,
    onChangeState: (FloatingButtonState) -> Unit
) {

    val icon = if (state.isCollapsed) Icons.Default.MenuOpen else Icons.Default.Search

    val nextState = when (state) {
        FloatingButtonState.Expanded, FloatingButtonState.Collapsed -> {
            state.getNext
        }
        else -> state
    }

    val transition = updateTransition(targetState = state, label = "transitionWidth")
    val animateWidth = transition.animateFloat(label = "animateWidth") {
        when (it) {
            FloatingButtonState.SearchView -> 0.7f
            else -> 0.2f
        }
    }.value

    FloatingActionButton(
        modifier = Modifier
            .padding(start = 12.dp)
            .fillMaxWidth(animateWidth)
            .pushedAnimation(enabled = !state.isSearchView) { onChangeState(nextState) }
        ,
        onClick = { },
        backgroundColor = MaterialTheme.colors.secondary
    ) {
        AnimatedContent(targetState = state) { animatedState ->
            when (animatedState) {
                FloatingButtonState.SearchView -> {
                    SearchTextField(
                        query = query,
                        onQueryChange = onQueryChange,
                        onSearchFocusChange = onSearchFocusChange,
                        onClearQuery = onClearQuery,
                        focused = focused
                    )
                }
                else -> {
                    Icon(
                        imageVector = icon,
                        tint = Color.White,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            }
        }
    }
}
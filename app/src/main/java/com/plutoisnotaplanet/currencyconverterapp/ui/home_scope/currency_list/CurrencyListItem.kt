package com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.currency_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plutoisnotaplanet.currencyconverterapp.R
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.CurrencyListType
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.CurrencyViewItem
import com.plutoisnotaplanet.currencyconverterapp.application.utils.ResourceUtil.painterResourceByName
import com.plutoisnotaplanet.currencyconverterapp.ui.components.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Preview(showBackground = true)
@Composable
fun CurrencyListItem(
    modifier: Modifier = Modifier,
    item: CurrencyViewItem = CurrencyViewItem.getEmpty(),
    selectedCurrencyName: String = "USD",
    currentListType: CurrencyListType = CurrencyListType.POPULAR,
    onAction: (CurrencyScreenAction) -> Unit = {}
) {
    val heartButtonState = remember(item.isFavorite) {
        mutableStateOf(
            if (item.isFavorite)
                HeartButtonState.ACTIVE
            else
                HeartButtonState.IDLE
        )
    }

    val commonTextColor = if (item.code == selectedCurrencyName)
        MaterialTheme.colors.onSecondary
    else
        MaterialTheme.colors.onSurface

    val backgroundColor = if (item.code == selectedCurrencyName)
        MaterialTheme.colors.primary
    else
        MaterialTheme.colors.surface

    val subTitleTextColor = if (item.code == selectedCurrencyName)
        MaterialTheme.colors.surface.copy(0.6f)
    else
        MaterialTheme.colors.primary.copy(0.6f)

    Surface(
        modifier = modifier
            .height(104.dp)
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
            .clickable {
                onAction(
                    CurrencyScreenAction.SelectCurrency(
                        currency = item,
                        listType = currentListType
                    )
                )
            },
        elevation = 4.dp,
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        color = backgroundColor
    ) {

        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 12.dp)
                .fillMaxSize()
        ) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.CenterVertically),
                    painter = painterResourceByName(name = "usd_${item.code}"),
                    contentDescription = null,
                )
                DefaultTitle(
                    value = item.countryName,
                    textAlign = TextAlign.Start,
                    textColor = commonTextColor,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f, true)
                        .align(Alignment.CenterVertically),
                    maxLines = 1,
                )
                AnimatedHeartButton(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    buttonState = heartButtonState,
                    onToggle = {
                        onAction(CurrencyScreenAction.ToggleFavorite(item))
                        heartButtonState.value =
                            if (heartButtonState.value == HeartButtonState.IDLE) HeartButtonState.ACTIVE else HeartButtonState.IDLE
                    },
                    iconSize = 36.dp,
                    expandIconSize = 42.dp
                )
            }

            InfoField(
                title = stringResource(
                    id = R.string.tv_exchange_rate
                ),
                value = stringResource(
                    id = R.string.tv_for_base_currency_this_rate,
                    selectedCurrencyName,
                    item.rate,
                    item.code
                ),
                valueTextColor = commonTextColor,
                subTitleTextColor = subTitleTextColor,
            )
        }

    }

}

@Composable
fun InfoField(
    title: String,
    value: String,
    subTitleTextColor: Color,
    valueTextColor: Color
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        DefaultSubTitle(
            value = title,
            fontSize = 12,
            textColor = subTitleTextColor,
            maxLines = 1,
        )

        Spacer(modifier = Modifier.height(2.dp))

        DefaultBodyText(
            value = value,
            fontSize = 14,
            maxLines = 1,
            textColor = valueTextColor
        )
    }
}
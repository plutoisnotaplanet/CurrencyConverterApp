package com.plutoisnotaplanet.currencyconverterapp.ui.home_scope.currency_list.sort_screen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.plutoisnotaplanet.currencyconverterapp.R
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.SortBy
import com.plutoisnotaplanet.currencyconverterapp.application.domain.model.SortSettings
import com.plutoisnotaplanet.currencyconverterapp.ui.components.DefaultClickableText
import com.plutoisnotaplanet.currencyconverterapp.ui.components.DefaultSubTitle
import com.plutoisnotaplanet.currencyconverterapp.ui.components.DefaultTitle
import com.plutoisnotaplanet.currencyconverterapp.ui.components.SelectableChip

@Composable
fun CurrencySortScreen(
    sortSettings: SortSettings,
    updateSortSettings: (SortSettings) -> Unit
) {

    val sortingList = remember {
        derivedStateOf { SortBy.values().toList().drop(1) }
    }

    Row(
        modifier = Modifier
            .padding(top = 16.dp, start = 12.dp, end = 12.dp)
            .fillMaxWidth()
            .height(36.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        DefaultTitle(
            value = stringResource(id = R.string.tt_sort_to),
            modifier = Modifier
        )

        DefaultClickableText(
            modifier = Modifier,
            onClick = {
                updateSortSettings(
                    sortSettings.copy(
                        sortByRate = SortBy.None,
                        sortByName = SortBy.None
                    )
                )
            },
            title = stringResource(id = R.string.tt_clear_sort)
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    DefaultSubTitle(
        modifier = Modifier.padding(start = 12.dp),
        value = stringResource(id = R.string.tt_sort_by_name)
    )

    SelectableChipGroup(list = sortingList.value, selectedChip = sortSettings.sortByName) { sortBy ->
        updateSortSettings(sortSettings.copy(sortByName = sortBy))
    }

    DefaultSubTitle(
        modifier = Modifier.padding(start = 12.dp),
        value = stringResource(id = R.string.tt_sort_by_rate)
    )

    SelectableChipGroup(list = sortingList.value, selectedChip = sortSettings.sortByRate) { sortBy ->
        updateSortSettings(sortSettings.copy(sortByRate = sortBy))
    }

}


@Composable
fun SelectableChipGroup(
    list: List<SortBy>,
    selectedChip: SortBy,
    onSelected: (SortBy) -> Unit
) {
    Row(modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        list.forEach { item ->
            SelectableChip(
                value = item.viewValue,
                isSelected = item == selectedChip,
                onSelected = { onSelected(item) }
            )
        }
    }
}

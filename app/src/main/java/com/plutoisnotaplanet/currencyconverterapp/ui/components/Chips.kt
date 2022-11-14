package com.plutoisnotaplanet.currencyconverterapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.plutoisnotaplanet.currencyconverterapp.R
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plutoisnotaplanet.currencyconverterapp.ui.theme.light_primary

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun CancellableChip(
    modifier: Modifier = Modifier.padding(4.dp),
    value: String = stringResource(id = R.string.tv_unknown),
    onClick: () -> Unit = {},
    onCancel: () -> Unit = {}
) {
    FilterChip(
        modifier = modifier,
        selected = false,
        onClick = onClick,
        trailingIcon = {
            IconButton(
                onClick = onCancel,
                modifier = Modifier
                    .size(20.dp)
                    .padding(top = 2.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    tint = Color.Black,
                    contentDescription = null
                )
            }
        }
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(end = 8.dp)
        )
    }
}

@Preview
@Composable
fun DefaultChip(
    modifier: Modifier = Modifier.padding(4.dp),
    backgroundColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
        .compositeOver(MaterialTheme.colors.surface),
    value: String = stringResource(id = R.string.tv_unknown),
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier,
        elevation = 4.dp,
        shape = MaterialTheme.shapes.medium.copy(CornerSize(percent = 15)),
        color = backgroundColor
    ) {
        Row(modifier = Modifier
            .clickable(onClick = onClick)
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.body2,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview
@Composable
fun SelectableChip(
    modifier: Modifier = Modifier.padding(4.dp),
    value: String = stringResource(id = R.string.tv_unknown),
    isSelected: Boolean = false,
    onSelected: () -> Unit = {}
) {
    Surface(
        modifier = modifier,
        elevation = 4.dp,
        shape = MaterialTheme.shapes.medium.copy(CornerSize(percent = 15)),
        color = if (isSelected) MaterialTheme.colors.primary else Color.Gray
    ) {
        Row(modifier = Modifier
            .toggleable(
                value = isSelected,
                onValueChange = { onSelected() }
            )
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.body2,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
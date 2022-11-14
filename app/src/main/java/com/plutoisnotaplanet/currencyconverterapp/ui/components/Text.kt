package com.plutoisnotaplanet.currencyconverterapp.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plutoisnotaplanet.currencyconverterapp.R
import com.plutoisnotaplanet.currencyconverterapp.ui.components.modifier.pushedAnimation

@Preview(showBackground = true)
@Composable
fun DefaultSubTitle(
    modifier: Modifier = Modifier,
    fontSize: Int = 14,
    maxLines: Int = 1,
    textColor: Color = Color.Black,
    value: String = stringResource(id = R.string.tv_unknown),
) {
    Text(
        text = value,
        style = MaterialTheme.typography.subtitle1,
        fontSize = fontSize.sp,
        color = textColor,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}

@Preview
@Composable
fun DefaultTitle(
    modifier: Modifier = Modifier,
    textColor: Color = Color.Black,
    fontSize: Int = 16,
    maxLines: Int = 1,
    textAlign: TextAlign? = null,
    value: String = stringResource(id = R.string.tv_unknown),
) {
    Text(
        text = value,
        style = MaterialTheme.typography.h6,
        fontSize = fontSize.sp,
        color = textColor,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}

@Preview
@Composable
fun DefaultBodyText(
    modifier: Modifier = Modifier,
    fontSize: Int = 14,
    maxLines: Int = 1,
    textColor: Color = Color.Black,
    value: String = stringResource(id = R.string.tv_unknown)
) {
    Text(
        text = value,
        style = MaterialTheme.typography.h6,
        fontSize = fontSize.sp,
        color = textColor,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultClickableText(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.tv_unknown),
    onClick: (Int) -> Unit = {},
) {
    ClickableText(
        modifier = modifier.pushedAnimation { onClick(0) },
        onClick = { },
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = MaterialTheme.colors.secondary)) {
                append(title)
            }
        }
    )
}
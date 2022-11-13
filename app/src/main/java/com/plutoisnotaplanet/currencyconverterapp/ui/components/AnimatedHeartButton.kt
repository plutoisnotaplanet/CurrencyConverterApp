package com.plutoisnotaplanet.currencyconverterapp.ui.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.plutoisnotaplanet.currencyconverterapp.ui.components.modifier.pushedAnimation
import kotlinx.coroutines.ExperimentalCoroutinesApi

enum class HeartButtonState {
    IDLE, ACTIVE
}

@Preview
@ExperimentalCoroutinesApi
@Composable
fun AnimatedHeartButton(
    modifier: Modifier = Modifier,
    buttonState: MutableState<HeartButtonState> = mutableStateOf(HeartButtonState.IDLE),
    onToggle: () -> Unit = {},
    iconSize: Dp = 50.dp,
    expandIconSize: Dp = 80.dp,
) {

    val heartTransition = updateTransition(targetState = buttonState.value, label = "heartButton")

    val size = heartTransition.animateDp(
        transitionSpec = {
            keyframes {
                durationMillis = 500
                expandIconSize at 100
                iconSize at 200
            }
        },
        label = "size"
    ) {
        when (it) {
            HeartButtonState.IDLE -> iconSize
            HeartButtonState.ACTIVE -> iconSize
        }
    }
    val color = heartTransition.animateColor(
        transitionSpec = {
            tween(durationMillis = 500)
        },
        label = "color"
    ) {
        when (it) {
            HeartButtonState.IDLE -> Color.LightGray
            HeartButtonState.ACTIVE -> Color.Red
        }
    }

    HeartButton(
        modifier = modifier,
        onToggle = onToggle,
        color = color.value,
        size = size.value
    )
}

@ExperimentalCoroutinesApi
@Composable
private fun HeartButton(
    modifier: Modifier,
    color: Color,
    size: Dp,
    onToggle: () -> Unit,
) {
    Box(modifier = Modifier
        .size(38.dp)
        .fillMaxSize()
        .offset(x = 8.dp)) {
        Icon(
            modifier = modifier
                .width(size)
                .height(size)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                    onClick = onToggle
                ),
            imageVector = Icons.Outlined.Favorite,
            tint = color,
            contentDescription = null
        )
    }
}
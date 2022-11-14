package com.plutoisnotaplanet.currencyconverterapp.ui.components.modifier

import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInteropFilter

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.pushedAnimation(
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null,
): Modifier = composed {

    if (!enabled) return@composed this

    val touched = remember { mutableStateOf(false) }
    val scale = animateFloatAsState(if (touched.value && enabled) 0.95f else 1f)

    this
        .scale(scale.value)
        .pointerInteropFilter {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    touched.value = true
                }
                MotionEvent.ACTION_UP -> {
                    touched.value = false
                    if (enabled && onClick != null) {
                        onClick()
                    }
                }
                MotionEvent.ACTION_CANCEL -> {
                    touched.value = false
                }
            }
            true
        }
}
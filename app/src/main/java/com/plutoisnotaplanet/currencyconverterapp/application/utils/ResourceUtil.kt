package com.plutoisnotaplanet.currencyconverterapp.application.utils

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.plutoisnotaplanet.currencyconverterapp.R

object ResourceUtil {

    @SuppressLint("DiscouragedApi")
    fun Context.getDrawableResourceByName(name: String): Int {
        return resources.getIdentifier(name.lowercase(), "drawable", packageName)
    }

    @Composable
    fun painterResourceByName(name: String): Painter {
        val context = LocalContext.current
        val resId = context.getDrawableResourceByName(name).takeIf { it != 0 } ?: R.drawable.usd_eur
        return painterResource(id = resId)
    }
}
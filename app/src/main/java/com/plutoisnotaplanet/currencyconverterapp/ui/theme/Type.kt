package com.plutoisnotaplanet.currencyconverterapp.ui.theme


import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.plutoisnotaplanet.currencyconverterapp.R

// Set of Material typography styles to start with
private val RobotoFonts = FontFamily(
    Font(R.font.roboto_light, FontWeight.Light),
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_black, FontWeight.Black),
)

val Typography = Typography(
    h4 = TextStyle(
        fontFamily = RobotoFonts,
        fontWeight = FontWeight.W700,
        fontSize = 32.sp
    ),
    h5 = TextStyle(
        fontFamily = RobotoFonts,
        fontWeight = FontWeight.W600,
        fontSize = 24.sp
    ),
    h6 = TextStyle(
        fontFamily = RobotoFonts,
        fontWeight = FontWeight.W600,
        fontSize = 20.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = RobotoFonts,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = RobotoFonts,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    body2 = TextStyle(
        fontFamily = RobotoFonts,
        fontSize = 14.sp
    )
)
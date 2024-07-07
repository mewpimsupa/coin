package com.pimsupa.coin.util

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.pimsupa.coin.ui.theme.*

data class CoinColor(
    val white: Color,
    val allBlack: Color,
    val black: Color,
    val lightGrey: Color,
    val lightGrey2: Color,
    val lightGrey3: Color,
    val grey: Color,
    val green: Color,
    val red: Color,
    val yellow: Color,
    val blue: Color,
    val lightBlue: Color
)

val LightCoinColors = CoinColor(
    white = White,
    allBlack = AllBlack,
    black = Black,
    lightGrey = LightGrey,
    lightGrey2 = LightGrey2,
    lightGrey3 = LightGrey3,
    grey = Grey,
    green = Green,
    red = Red,
    yellow = Yellow,
    blue = Blue,
    lightBlue = LightBlue
)

val DarkCoinColors = CoinColor(
    white = Black,
    allBlack = White,
    black = White,
    lightGrey = DarkThemeLightGrey,
    lightGrey2 = DarkThemeLightGrey2,
    lightGrey3 = DarkThemeLightGrey3,
    grey = Grey,
    green = Green,
    red = Yellow,
    yellow = Yellow,
    blue = Blue,
    lightBlue = LightBlue
)

val LocalCoinColor = staticCompositionLocalOf { LightCoinColors }
package com.pimsupa.coin.util

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import com.pimsupa.coin.ui.theme.*

data class CoinColor(
    val allBlack: Color = AllBlack,
    val black: Color = Black,
    val lightGrey: Color = LightGrey,
    val lightGrey2: Color = LightGrey2,
    val grey: Color = Grey,
    val green: Color = Green,
    val red: Color = Red,
    val yellow: Color = Yellow,
    val blue: Color = Blue,
    val lightBlue: Color = LightBlue
)


val LocalCoinColor = compositionLocalOf { CoinColor() }
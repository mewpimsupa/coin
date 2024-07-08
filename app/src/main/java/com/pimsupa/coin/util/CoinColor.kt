package com.pimsupa.coin.util

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.pimsupa.coin.ui.theme.*

data class CoinColor(
    val blue: Color,
    val background: Color,
    val highlight: Color,
    val textColor: Color,
    val textInviteColor: Color,
    val card: Color,
    val search: Color,
    val containerSearch: Color,
    val positiveChange: Color,
    val negativeChange: Color,
    val textDetail: Color,
    val inviteCard: Color,
)

val LightCoinColors = CoinColor(
    blue = Blue,
    background = White,
    textColor = Black,
    textInviteColor = AllBlack,
    card = LightGrey,
    containerSearch = LightGrey2,
    search = LightGrey3,
    textDetail = Grey,
    positiveChange = Green,
    negativeChange = Red,
    highlight = Red,
    inviteCard = LightBlue
)

val DarkCoinColors = CoinColor(
    blue = Blue,
    background = Black,
    textColor = White,
    textInviteColor = AllBlack,
    card = DarkThemeLightGrey,
    containerSearch = DarkThemeLightGrey2,
    search = DarkThemeLightGrey3,
    textDetail = Grey,
    positiveChange = Green,
    negativeChange = Red,
    highlight = Yellow,
    inviteCard = LightBlue
)

val LocalCoinColor = staticCompositionLocalOf { LightCoinColors }
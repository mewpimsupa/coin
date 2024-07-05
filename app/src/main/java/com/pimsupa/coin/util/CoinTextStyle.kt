package com.pimsupa.coin.util

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class CoinTextStyle(
    val header1: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 23.44.sp
    ),
    val header2: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 21.09.sp
    ),
    val titleBold: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 18.75.sp
    ),
    val title: TextStyle = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 18.75.sp
    ),
    val detailBold1: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 14.06.sp
    ),
    val detailBold2: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 16.41.sp
    ),
    val detail1: TextStyle = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        lineHeight = 14.06.sp
    ),
    val detail2: TextStyle = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        lineHeight = 16.41.sp
    ),
)

val LocalCoinTextStyle = compositionLocalOf { CoinTextStyle() }

package com.pimsupa.coin.util

import androidx.compose.ui.graphics.Color

fun String.parseColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
}
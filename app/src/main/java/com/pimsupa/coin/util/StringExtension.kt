package com.pimsupa.coin.util

import androidx.compose.ui.graphics.Color
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

fun String.parseColor(): Color? {
    return try {
        Color(android.graphics.Color.parseColor(this))
    } catch (e: Exception) {
        null
    }
}

fun formatDecimal(value: String, scale: Int): String {
    return try {
        val bd = BigDecimal(value).setScale(scale, RoundingMode.HALF_UP)
        val df = DecimalFormat("#,##0.${"0".repeat(scale)}")
        df.format(bd)
    } catch (e: NumberFormatException) {
        ""
    }
}
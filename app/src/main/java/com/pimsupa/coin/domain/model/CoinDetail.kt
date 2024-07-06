package com.pimsupa.coin.domain.model

import com.pimsupa.coin.data.coindetail.AllTimeHigh
import com.pimsupa.coin.data.coindetail.Link
import com.pimsupa.coin.data.coindetail.Notice
import com.pimsupa.coin.data.coindetail.Supply
import com.pimsupa.coin.util.formatDecimal
import com.squareup.moshi.Json
import java.text.DecimalFormat

data class CoinDetail(
    val color: String = "",
    val description: String = "",
    val iconUrl: String = "",
    val marketCap: String = "",
    val name: String = "",
    val price: String = "",
    val symbol: String = "",
    val uuid: String = "",
    val websiteUrl: String = ""
) {
    fun symbolDetail(): String = "(${symbol})"
    fun getCoinDetailPrice(): String = "$ ${formatDecimal(price, 2)}"
    fun getCoinMarketCap(): String = "$ ${formatLargeNumber(marketCap)}"
    fun formatLargeNumber(numberStr: String): String {
        return try {
            val number = numberStr.toLong()
            val trillion = 1_000_000_000_000L
            val billion = 1_000_000_000L
            val million = 1_000_000L

            val decimalFormat = DecimalFormat("#,##0.00")

            when {
                number >= trillion -> {
                    val formatted = number.toDouble() / trillion
                    decimalFormat.format(formatted) + " " + "trillion"
                }

                number >= billion -> {
                    val formatted = number.toDouble() / billion
                    decimalFormat.format(formatted) + " " + "billion"
                }

                number >= million -> {
                    val formatted = number.toDouble() / million
                    decimalFormat.format(formatted) + " " + "million"
                }

                else -> decimalFormat.format(number)
            }
        } catch (e: NumberFormatException) {
            "Invalid number"
        }
    }
}

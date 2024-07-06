package com.pimsupa.coin.domain.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.abs

data class Coin(
    val name: String = "",
    val iconUrl: String = "",
    val btcPrice: String = "",
    val change: String = "",
    val coinRankingUrl: String = "",
    val color: String = "",
    val contractAddresses: List<String> = listOf(),
    val hVolume: String = "",
    val listedAt: Int = -1,
    val lowVolume: Boolean = false,
    val marketCap: String = "",
    val price: String = "",
    val rank: Int = -1,
    val sparkline: List<String> = listOf(),
    val symbol: String = "",
    val tier: Int = -1,
    val uuid: String = "",
) {
    fun symbolDetail(): String = "(${symbol})"

    fun getCoinListPrice(): String = "$${formatDecimal(price, 5)}"
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

    fun formatDecimal(value: String, scale: Int): String {
        return try {
            val bd = BigDecimal(value).setScale(scale, RoundingMode.HALF_UP)
            val df = DecimalFormat("#,##0.${"0".repeat(scale)}")
            df.format(bd)
        } catch (e: NumberFormatException) {
            ""
        }
    }

    fun isPositiveChange(): Pair<Boolean, String> {
        return try {
            val value = change.toDouble()
            val isPositive = value > 0
            Pair(isPositive, formatDecimal(value.makePositive().toString(), 2))
        } catch (e: NumberFormatException) {
            Pair(false, "")
        }
    }

    private fun Double.makePositive(): Double {
        return abs(this)
    }
}
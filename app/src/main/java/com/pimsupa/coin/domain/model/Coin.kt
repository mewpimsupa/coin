package com.pimsupa.coin.domain.model


import com.pimsupa.coin.util.formatDecimal
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

    fun getCoinListPrice(): String = "$${formatDecimal(price, 5)}"

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
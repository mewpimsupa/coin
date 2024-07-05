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
    fun roundPrice(): String {
        return try {
            val bd = BigDecimal(price).setScale(5, RoundingMode.HALF_UP)
            val df = DecimalFormat("#,##0.00000")
            "$${df.format(bd)}"
        } catch (e: NumberFormatException) {
            // Handle the case where the input string is not a valid number
            ""
        }
    }

    private fun Double.roundTwoDecimal(): String {
        return try {
            val bd = BigDecimal(this).setScale(2, RoundingMode.HALF_UP)
            val df = DecimalFormat("#,##0.00")
            "$${df.format(bd)}"
        } catch (e: NumberFormatException) {
            // Handle the case where the input string is not a valid number
            ""
        }
    }

    fun isPositiveChange(): Pair<Boolean, String> {
        return try {
            val value = change.toDouble()
            if (value > 0) {
                Pair(true, makePositive(value).roundTwoDecimal())
            } else {
                Pair(false, makePositive(value).roundTwoDecimal())
            }
        } catch (e: NumberFormatException) {
            Pair(false, "")
        }
    }

    private fun makePositive(number: Double): Double {
        return abs(number)
    }
}
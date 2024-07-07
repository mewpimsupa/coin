package com.pimsupa.coin.data.remote.getcoins


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Stats(
    @Json(name = "total")
    val total: Int,
    @Json(name = "total24hVolume")
    val total24hVolume: String,
    @Json(name = "totalCoins")
    val totalCoins: Int,
    @Json(name = "totalExchanges")
    val totalExchanges: Int,
    @Json(name = "totalMarketCap")
    val totalMarketCap: String,
    @Json(name = "totalMarkets")
    val totalMarkets: Int
)
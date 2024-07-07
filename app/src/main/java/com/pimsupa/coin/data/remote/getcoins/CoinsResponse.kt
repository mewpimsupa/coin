package com.pimsupa.coin.data.remote.getcoins


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoinsResponse(
    @Json(name = "coins")
    val coins: List<Coin>,
    @Json(name = "stats")
    val stats: Stats
)
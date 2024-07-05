package com.pimsupa.coin.data.getcoins


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Coin(
    @Json(name = "btcPrice")
    val btcPrice: String,
    @Json(name = "change")
    val change: String,
    @Json(name = "coinrankingUrl")
    val coinRankingUrl: String,
    @Json(name = "color")
    val color: String,
    @Json(name = "contractAddresses")
    val contractAddresses: List<String>,
    @Json(name = "24hVolume")
    val hVolume: String,
    @Json(name = "iconUrl")
    val iconUrl: String,
    @Json(name = "listedAt")
    val listedAt: Int,
    @Json(name = "lowVolume")
    val lowVolume: Boolean,
    @Json(name = "marketCap")
    val marketCap: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "price")
    val price: String,
    @Json(name = "rank")
    val rank: Int,
    @Json(name = "sparkline")
    val sparkline: List<String>,
    @Json(name = "symbol")
    val symbol: String,
    @Json(name = "tier")
    val tier: Int,
    @Json(name = "uuid")
    val uuid: String
)
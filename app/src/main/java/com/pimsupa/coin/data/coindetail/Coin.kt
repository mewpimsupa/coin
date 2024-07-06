package com.pimsupa.coin.data.coindetail


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Coin(
    @Json(name = "allTimeHigh")
    val allTimeHigh: AllTimeHigh?,
    @Json(name = "btcPrice")
    val btcPrice: String?,
    @Json(name = "change")
    val change: String?,
    @Json(name = "coinrankingUrl")
    val coinrankingUrl: String?,
    @Json(name = "color")
    val color: String?,
    @Json(name = "contractAddresses")
    val contractAddresses: List<String>?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "fullyDilutedMarketCap")
    val fullyDilutedMarketCap: String?,
    @Json(name = "24hVolume")
    val hVolume: String?,
    @Json(name = "iconUrl")
    val iconUrl: String?,
    @Json(name = "links")
    val links: List<Link>?,
    @Json(name = "listedAt")
    val listedAt: Int?,
    @Json(name = "lowVolume")
    val lowVolume: Boolean?,
    @Json(name = "marketCap")
    val marketCap: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "notices")
    val notices: List<Notice>?,
    @Json(name = "numberOfExchanges")
    val numberOfExchanges: Int?,
    @Json(name = "numberOfMarkets")
    val numberOfMarkets: Int?,
    @Json(name = "price")
    val price: String?,
    @Json(name = "priceAt")
    val priceAt: Int?,
    @Json(name = "rank")
    val rank: Int?,
    @Json(name = "sparkline")
    val sparkline: List<String>?,
    @Json(name = "supply")
    val supply: Supply?,
    @Json(name = "symbol")
    val symbol: String?,
    @Json(name = "tags")
    val tags: List<String>?,
    @Json(name = "uuid")
    val uuid: String?,
    @Json(name = "websiteUrl")
    val websiteUrl: String?
)
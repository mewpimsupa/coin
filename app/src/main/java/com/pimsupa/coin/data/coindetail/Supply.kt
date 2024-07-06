package com.pimsupa.coin.data.coindetail


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Supply(
    @Json(name = "circulating")
    val circulating: String,
    @Json(name = "confirmed")
    val confirmed: Boolean,
    @Json(name = "max")
    val max: String,
    @Json(name = "supplyAt")
    val supplyAt: Int,
    @Json(name = "total")
    val total: String
)
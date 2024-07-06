package com.pimsupa.coin.data.coindetail


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AllTimeHigh(
    @Json(name = "price")
    val price: String?,
    @Json(name = "timestamp")
    val timestamp: Int?
)
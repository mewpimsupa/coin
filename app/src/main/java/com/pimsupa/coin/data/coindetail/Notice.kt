package com.pimsupa.coin.data.coindetail


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Notice(
    @Json(name = "type")
    val type: String,
    @Json(name = "value")
    val value: String
)
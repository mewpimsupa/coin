package com.pimsupa.coin.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "coin")
data class CoinEntity(
    val name: String? = "",
    val iconUrl: String? = "",
    val change: String? = "",
    val price: String? = "",
    val rank: Int? = -1,
    val symbol: String? = "",
    @PrimaryKey val uuid: String = ""
)
package com.pimsupa.coin.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.pimsupa.coin.data.local.CoinTypeConverters
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@TypeConverters(CoinTypeConverters::class)
@Entity(tableName = "coin")
data class CoinEntity(
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
    @PrimaryKey val uuid: String = ""
)
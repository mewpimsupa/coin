package com.pimsupa.coin.domain.model

import com.pimsupa.coin.data.coindetail.AllTimeHigh
import com.pimsupa.coin.data.coindetail.Link
import com.pimsupa.coin.data.coindetail.Notice
import com.pimsupa.coin.data.coindetail.Supply
import com.squareup.moshi.Json

data class CoinDetail(
    val color: String,
    val description: String,
    val iconUrl: String,
    val marketCap: String,
    val name: String,
    val price: String,
    val symbol: String,
    val uuid: String,
    val websiteUrl: String
)

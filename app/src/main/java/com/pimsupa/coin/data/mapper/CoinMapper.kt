package com.pimsupa.coin.data.mapper

import com.pimsupa.coin.data.remote.coindetail.Coin
import com.pimsupa.coin.domain.model.CoinDetail

fun com.pimsupa.coin.data.remote.getcoins.Coin.toCoin(): com.pimsupa.coin.domain.model.Coin {
    return com.pimsupa.coin.domain.model.Coin(
        name = this.name ?: "",
        iconUrl = this.iconUrl ?: "",
        btcPrice = this.btcPrice ?: "",
        change = this.change ?: "",
        coinRankingUrl = this.coinRankingUrl ?: "",
        color = this.color ?: "",
        contractAddresses = this.contractAddresses ?: listOf(),
        hVolume = this.hVolume ?: "",
        listedAt = this.listedAt ?: 0,
        lowVolume = this.lowVolume ?: false,
        marketCap = this.marketCap ?: "",
        price = this.price ?: "",
        rank = this.rank ?: 0,
        sparkline = this.sparkline ?: listOf(),
        symbol = this.symbol ?: "",
        tier = this.tier ?: 0,
        uuid = this.uuid ?: "",
    )
}

fun Coin.toCoinDetail(): CoinDetail {
    return CoinDetail(
        color = this.color ?: "",
        description = this.description ?: "",
        iconUrl = this.iconUrl ?: "",
        marketCap = this.marketCap ?: "",
        name = this.name ?: "",
        price = this.price ?: "",
        symbol = this.symbol ?: "",
        uuid = this.uuid ?: "",
        websiteUrl = this.websiteUrl ?: ""
    )
}

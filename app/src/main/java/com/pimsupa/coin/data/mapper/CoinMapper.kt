package com.pimsupa.coin.data.mapper

import com.pimsupa.coin.data.getcoins.Coin
import com.pimsupa.coin.domain.model.CoinDetail

fun Coin.toCoin(): com.pimsupa.coin.domain.model.Coin {
    return com.pimsupa.coin.domain.model.Coin(
        name = this.name,
        iconUrl = this.iconUrl,
        btcPrice = this.btcPrice,
        change = this.change,
        coinRankingUrl = this.coinRankingUrl,
        color = this.color,
        contractAddresses = this.contractAddresses,
        hVolume = this.hVolume,
        listedAt = this.listedAt,
        lowVolume = this.lowVolume,
        marketCap = this.marketCap,
        price = this.price,
        rank = this.rank,
        sparkline = this.sparkline,
        symbol = this.symbol,
        tier = this.tier,
        uuid = this.uuid,
    )
}

fun com.pimsupa.coin.data.coindetail.Coin.toCoinDetail(): CoinDetail {
    return CoinDetail(
        color = this.color,
        description = this.description,
        iconUrl = this.iconUrl,
        marketCap = this.marketCap,
        name = this.name,
        price = this.price,
        symbol = this.symbol,
        uuid = this.uuid,
        websiteUrl = this.websiteUrl
    )
}

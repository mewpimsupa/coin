package com.pimsupa.coin.data.mapper

import com.pimsupa.coin.data.getcoins.Coin

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
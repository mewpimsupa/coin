package com.pimsupa.coin.data.mapper

import com.pimsupa.coin.data.getcoins.Coin

fun Coin.toCoin():com.pimsupa.coin.domain.model.Coin{
    return com.pimsupa.coin.domain.model.Coin(
        name = this.name
    )
}
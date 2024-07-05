package com.pimsupa.coin.ui.coinlist

import com.pimsupa.coin.domain.model.Coin

data class CoinListState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val coins: List<Coin> = listOf(),
    val isPagination:Boolean = false
) {
    fun getTop3Coins(): List<Coin> {
        return listOf()
    }
}

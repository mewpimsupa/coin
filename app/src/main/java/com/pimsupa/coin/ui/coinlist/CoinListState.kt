package com.pimsupa.coin.ui.coinlist

import com.pimsupa.coin.domain.model.Coin
import com.pimsupa.coin.util.StateEvent
import com.pimsupa.coin.util.consumed

data class CoinListState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val coins: List<Coin> = listOf(),
    val showCoinDetail: Coin? = null,
    var uiEvent: StateEvent<CoinListUiEvent> = consumed(),
) {
    fun getTop3Coins(): List<Coin> {
        return listOf()
    }
}

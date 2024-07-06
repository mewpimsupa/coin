package com.pimsupa.coin.ui.coinlist

import com.pimsupa.coin.domain.model.Coin

sealed class CoinListEvent {
    data object LoadCoins : CoinListEvent()
    data class OnClickCoin(val coin: Coin) : CoinListEvent()
    object OnDismissCoinDetail : CoinListEvent()
    data object OnClickLoadCoinsAgain : CoinListEvent()
    data object RefreshCoins : CoinListEvent()
}
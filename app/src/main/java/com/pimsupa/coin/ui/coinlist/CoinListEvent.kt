package com.pimsupa.coin.ui.coinlist

sealed class CoinListEvent {
    data object LoadCoins : CoinListEvent()
}
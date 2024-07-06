package com.pimsupa.coin.ui.coinlist

import com.pimsupa.coin.domain.model.Coin

sealed class CoinListUiEvent {
    data class OpenCoinDetail(val coin: Coin) : CoinListUiEvent()
    object CloseCoinDetail : CoinListUiEvent()
}
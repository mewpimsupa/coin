package com.pimsupa.coin.ui.coinlist

import com.pimsupa.coin.domain.model.Coin

sealed class CoinListUiEvent {
   data object OpenCoinDetail : CoinListUiEvent()
    data object CloseCoinDetail : CoinListUiEvent()
}
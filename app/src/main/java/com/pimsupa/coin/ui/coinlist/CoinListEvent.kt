package com.pimsupa.coin.ui.coinlist

import androidx.compose.ui.text.input.TextFieldValue
import com.pimsupa.coin.domain.model.Coin

sealed class CoinListEvent {
    data object LoadCoins : CoinListEvent()
    data class OnClickCoin(val coin: Coin) : CoinListEvent()
    object OnDismissCoinDetail : CoinListEvent()
    data object OnClickLoadCoinsAgain : CoinListEvent()
    data object RefreshCoins : CoinListEvent()
    data class OnSearch(val searchText: TextFieldValue) : CoinListEvent()
    data object ClearSearchText : CoinListEvent()
    data object LoadMoreCoins : CoinListEvent()
    data object OnClickInviteFriends : CoinListEvent()
}
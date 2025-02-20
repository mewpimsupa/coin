package com.pimsupa.coin.ui.coinlist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import com.pimsupa.coin.domain.model.Coin
import com.pimsupa.coin.domain.model.CoinDetail
import com.pimsupa.coin.util.StateEvent
import com.pimsupa.coin.util.consumed

data class CoinListState(
    val isLoading: Boolean = false,
    val isRefresh: Boolean = false,
    val isError: Boolean = false,
    val coins: List<Coin> = listOf(),
    val filteredCoins: List<ItemDisplay> = listOf(),
    val showCoinDetail: CoinDetail? = null,
    val isFullScreenLoading: Boolean = false,
    val searchText: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue("")),
    var uiEvent: StateEvent<CoinListUiEvent> = consumed(),
) {
    fun searchNotFound(): Boolean = searchText.value.text.isNotBlank() && filteredCoins.isEmpty()

    fun getTop3Coins(): List<Coin> {
        return coins.take(3)
    }
}

sealed class ItemDisplay {
    data object InviteFriends : ItemDisplay()
    data class CoinItem(val coin: Coin):ItemDisplay()
}

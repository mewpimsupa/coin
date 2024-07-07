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
    val showCoinDetail: CoinDetail? = null,
    val isFullScreenLoading: Boolean = false,
    val searchText: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue("")),
    val searchCoinsList: List<Coin> = listOf(),
    var uiEvent: StateEvent<CoinListUiEvent> = consumed(),
) {
    fun getTop3Coins(): List<Coin> {
        return listOf()
    }
}

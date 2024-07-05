package com.pimsupa.coin.ui.coinlist

import com.pimsupa.coin.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor() : BaseViewModel<CoinListState>(CoinListState()) {

    fun onEvent(event: CoinListEvent) {

    }
}
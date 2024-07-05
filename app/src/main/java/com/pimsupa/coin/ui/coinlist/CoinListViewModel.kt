package com.pimsupa.coin.ui.coinlist

import androidx.lifecycle.viewModelScope
import com.pimsupa.coin.domain.usecase.GetCoins
import com.pimsupa.coin.util.BaseViewModel
import com.pimsupa.coin.util.CoinException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(private val getCoins: GetCoins) :
    BaseViewModel<CoinListState>(CoinListState()) {

    fun onEvent(event: CoinListEvent) {

    }

    init {
        viewModelScope.launch {
            setState {
                copy(isLoading = true)
            }

            getCoins.invoke()
                .onSuccess {
                    setState {
                        copy(coins = it, isLoading = false)
                    }
                }
                .onFailure {
                    when (it) {
                        is CoinException.CoinIsNullException, is CoinException.GetCoinsErrorException -> {
                            setState { copy(isError = true, isLoading = false) }
                        }
                    }
                }
        }
    }
}
package com.pimsupa.coin.ui.coinlist

import android.util.Log
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

    private var currentPage = 0

    fun onEvent(event: CoinListEvent) {
        when (event) {
            is CoinListEvent.LoadCoins -> {
                loadCoins()
            }
        }
    }

    init {
        viewModelScope.launch {
            setState {
                copy(isLoading = true)
            }
            getCoins()
        }
    }

    private suspend fun getCoins() {
        getCoins.invoke(currentPage)
            .onSuccess {
                val coinList = uiState.value.coins.toMutableList()
                coinList.addAll(it)
                setState {
                    copy(
                        coins = coinList
                    )
                }
                setState { copy(isLoading = false) }
            }
            .onFailure {
                when (it) {
                    is CoinException.CoinIsNullException, is CoinException.GetCoinsErrorException -> {
                        setState {
                            copy(
                                isError = true,
                                isLoading = false,
                            )
                        }
                    }
                }
            }
    }


    private fun loadCoins() {
        viewModelScope.launch {
            if (uiState.value.coins.isEmpty()) return@launch
            setState {
                copy(isLoading = true)
            }
            currentPage++
            getCoins()
        }
    }
}
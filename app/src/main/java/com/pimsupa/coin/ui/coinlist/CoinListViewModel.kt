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

    private var currentPage = 0

    fun onEvent(event: CoinListEvent) {
        when (event) {
            is CoinListEvent.LoadCoins -> {
                loadCoins()
            }
        }
    }

    init {
        setState {
            copy(isLoading = true)
        }
        getCoins()
    }

    private fun getCoins() {
        viewModelScope.launch {
            getCoins.invoke(currentPage)
                .onSuccess {
                    setState {
                        copy(
                            coins = it,
                            isLoading = false,
                            isPagination = false
                        )
                    }
                }
                .onFailure {
                    when (it) {
                        is CoinException.CoinIsNullException, is CoinException.GetCoinsErrorException -> {
                            setState {
                                copy(
                                    isError = true,
                                    isLoading = false,
                                    isPagination = false
                                )
                            }
                        }
                    }
                }
        }

    }

    private fun loadCoins() {
        setState {
            copy(isPagination = true)
        }
        currentPage++
        getCoins()
    }
}
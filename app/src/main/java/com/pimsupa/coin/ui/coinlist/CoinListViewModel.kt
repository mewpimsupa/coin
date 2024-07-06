package com.pimsupa.coin.ui.coinlist

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.pimsupa.coin.domain.model.Coin
import com.pimsupa.coin.domain.usecase.GetCoinDetail
import com.pimsupa.coin.domain.usecase.GetCoins
import com.pimsupa.coin.util.BaseViewModel
import com.pimsupa.coin.util.CoinDispatchers
import com.pimsupa.coin.util.CoinException
import com.pimsupa.coin.util.Dispatcher
import com.pimsupa.coin.util.consumed
import com.pimsupa.coin.util.triggered
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoins: GetCoins,
    private val getCoinDetail: GetCoinDetail,
    @Dispatcher(CoinDispatchers.Default) private val defaultDispatcher: CoroutineDispatcher
) :
    BaseViewModel<CoinListState>(CoinListState()) {

    private var currentPage = 0

    fun onEvent(event: CoinListEvent) {
        when (event) {
            is CoinListEvent.LoadCoins -> loadCoins()

            is CoinListEvent.OnClickCoin -> onClickCoin(event.coin)

            is CoinListEvent.OnDismissCoinDetail -> onDismissCoinDetail()

            is CoinListEvent.OnClickLoadCoinsAgain -> onClickLoadCoinsAgain()
            is CoinListEvent.RefreshCoins -> refreshCoin()
        }
    }

    init {
        viewModelScope.launch {
            getCoins()
        }
    }

    private suspend fun getCoins() {
        getCoins.invoke(currentPage)
            .onStart {
                setState { copy(isLoading = true, isError = false) }
            }
            .onEach { data ->
                val updatedCoins = withContext(defaultDispatcher) {
                    val coinList = uiState.value.coins.toMutableList()
                    coinList.addAll(data)
                    coinList
                }
                setState {
                    copy(
                        coins = updatedCoins
                    )
                }
                currentPage++
            }
            .catch { _ ->
                setState {
                    copy(
                        isError = true,
                        isLoading = false,
                        isRefresh = false
                    )
                }
            }
            .onCompletion {
                setState {
                    copy(
                        isLoading = false,
                        isRefresh = false
                    )
                }
            }
            .collect()
    }

    private suspend fun getCoinDetail(coin: Coin) {
        getCoinDetail.invoke(coin.uuid)
            .onStart {
                //add fullscreen loading for better experience
                setState { copy(isFullScreenLoading = true) }
            }
            .onEach { data ->
                setState {
                    copy(
                        showCoinDetail = data,
                        uiEvent = triggered(CoinListUiEvent.OpenCoinDetail)
                    )
                }
            }
            .catch { e ->
                //no requirement
            }
            .onCompletion {
                setState { copy(isFullScreenLoading = false) }
            }
            .collect()
    }


    private fun loadCoins() {
        viewModelScope.launch {
            if (uiState.value.coins.isEmpty()) return@launch
            setState { copy(coins = listOf()) }
            currentPage = 0
            getCoins()
        }
    }

    private fun refreshCoin() {
        //TODO bug on refresh coin second time on error screen
        setState { copy(isRefresh = true) }
        loadCoins()
    }


    private fun onClickCoin(coin: Coin) {
        if (uiState.value.isFullScreenLoading) return
        viewModelScope.launch {
            getCoinDetail(coin)
        }
    }

    private fun onDismissCoinDetail() {
        setState {
            copy(
                showCoinDetail = null,
                uiEvent = triggered(CoinListUiEvent.CloseCoinDetail)
            )
        }
    }

    private fun onClickLoadCoinsAgain() {
        if (uiState.value.isLoading) return
        viewModelScope.launch {
            getCoins()
        }
    }

    fun onStateEventConsumed() {
        setState { copy(uiEvent = consumed()) }
    }
}
package com.pimsupa.coin.ui.coinlist

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.pimsupa.coin.domain.model.Coin
import com.pimsupa.coin.domain.usecase.GetCoinDetail
import com.pimsupa.coin.domain.usecase.GetCoins
import com.pimsupa.coin.domain.usecase.SearchCoins
import com.pimsupa.coin.util.BaseViewModel
import com.pimsupa.coin.util.CoinDispatchers
import com.pimsupa.coin.util.CoinException
import com.pimsupa.coin.util.Dispatcher
import com.pimsupa.coin.util.consumed
import com.pimsupa.coin.util.toTextFieldValue
import com.pimsupa.coin.util.triggered
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
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
    private val searchCoins: SearchCoins,
    @Dispatcher(CoinDispatchers.Default) private val defaultDispatcher: CoroutineDispatcher
) :
    BaseViewModel<CoinListState>(CoinListState()) {

    private var currentPage = 0

    private var searchJob: Job? = null


    fun onEvent(event: CoinListEvent) {
        when (event) {
            is CoinListEvent.LoadCoins -> loadCoins()
            is CoinListEvent.LoadMoreCoins -> loadMoreCoins()

            is CoinListEvent.OnClickCoin -> onClickCoin(event.coin)

            is CoinListEvent.OnDismissCoinDetail -> onDismissCoinDetail()

            is CoinListEvent.OnClickLoadCoinsAgain -> onClickLoadCoinsAgain()
            is CoinListEvent.RefreshCoins -> refreshCoin()

            is CoinListEvent.OnSearch -> searchText(event.searchText)
            is CoinListEvent.ClearSearchText -> clearSearchText()
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
                        coins = updatedCoins,
                        filteredCoins = updatedCoins.drop(3),
                        isError = false
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
                        uiEvent = triggered(CoinListUiEvent.OpenCoinDetail),
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
            getCoins()
        }
    }

    private fun loadMoreCoins() {
        if (uiState.value.searchText.value.text.isNotBlank()) return
        Log.d("test", "load more coins")
        loadCoins()
    }

    private fun refreshCoin() {
        //TODO fix bug on refresh coin second time on error screen
        setState { copy(coins = listOf(), isRefresh = true) }
        currentPage = 0
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

    private fun searchText(text: TextFieldValue) {
        if (text.text.isEmpty()) {
            setState { copy(filteredCoins = uiState.value.coins.drop(3)) }
            return
        }
        setState { copy(searchText = mutableStateOf(text)) }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            searchCoins.invoke(text.text)
                .onStart {
                    delay(500)
                    Log.d("performSearch", "Started search for query: $text")
                }
                .onEach { coins ->
                    Log.d("performSearch", "Received coins: $coins")
                    setState { copy(filteredCoins = coins, isError = false) }
                }
                .catch {
                    //no requirement
                    Log.e("performSearch", "Error during search", it)
                }
                .collect()
        }
    }

    private fun clearSearchText() {
        setState {
            copy(
                searchText = mutableStateOf("".toTextFieldValue()),
                filteredCoins = uiState.value.coins.drop(3)
            )
        }
    }

    fun onStateEventConsumed() {
        setState { copy(uiEvent = consumed()) }
    }
}
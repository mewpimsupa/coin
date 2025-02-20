package com.pimsupa.coin.ui.coinlist

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.pimsupa.coin.domain.model.Coin
import com.pimsupa.coin.domain.usecase.GetCoinDetail
import com.pimsupa.coin.domain.usecase.GetCoins
import com.pimsupa.coin.domain.usecase.GetCoinsLocal
import com.pimsupa.coin.domain.usecase.SearchCoins
import com.pimsupa.coin.domain.usecase.UpdateCoin
import com.pimsupa.coin.domain.worker.UpdateCoinWorker
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoins: GetCoins,
    private val getCoinsLocal: GetCoinsLocal,
    private val getCoinDetail: GetCoinDetail,
    private val searchCoins: SearchCoins,
    private val updateCoin: UpdateCoin,
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

            is CoinListEvent.OnClickInviteFriends -> onClickInviteFiends()
        }
    }

    init {
        viewModelScope.launch {
            getCoins()
            updateCoin {
                viewModelScope.launch {
                    val coins = getCoinsLocal.invoke().first()
                    val filterCoins =
                        coins.filterOutTop3().calculateInviteFriendsList()
                    setState { copy(coins = coins, filteredCoins = filterCoins) }
                }
            }.collect()

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
                val filteredCoins = updatedCoins.filterOutTop3().calculateInviteFriendsList()
                setState {
                    copy(
                        coins = updatedCoins,
                        filteredCoins = filteredCoins,
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
        Log.d("fetchCoins", "load more coins")
        loadCoins()
    }

    private fun refreshCoin() {
        setState { copy(coins = listOf(), isRefresh = true) }
        currentPage = 0
        clearSearchText()
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
        viewModelScope.launch {
            searchJob?.cancel()
            if (text.text.isEmpty()) {
                clearSearchText()
                return@launch
            }
            setState { copy(searchText = mutableStateOf(text)) }
            searchJob = viewModelScope.launch {
                searchCoins.invoke(text.text)
                    .onStart {
                        delay(500)
                        Log.d("performSearch", "Started search for query: $text")
                    }
                    .onEach { coins ->
                        Log.d("performSearch", "Received coins: $coins")
                        val filteredCoins = coins.calculateInviteFriendsList()
                        setState {
                            copy(
                                filteredCoins = filteredCoins,
                                isError = false
                            )
                        }
                    }
                    .catch {
                        //no requirement
                        Log.e("performSearch", "Error during search", it)
                    }
                    .collect()
            }

        }
    }

    private fun clearSearchText() {
        viewModelScope.launch {
            val filteredCoins = uiState.value.coins.filterOutTop3().calculateInviteFriendsList()
            setState {
                copy(
                    searchText = mutableStateOf("".toTextFieldValue()),
                    filteredCoins = filteredCoins
                )
            }
        }

    }

    private fun onClickInviteFiends() {
        setState {
            copy(
                uiEvent = triggered(CoinListUiEvent.OpenInviteFriends("https://careers.lmwn.com"))
            )
        }
    }

    fun List<Coin>.filterOutTop3(): List<Coin> {
        return this.drop(3)
    }

    private suspend fun List<Coin>.calculateInviteFriendsList(): List<ItemDisplay> =
        withContext(defaultDispatcher) {
            var position = 5
            val displayCoinList: MutableList<ItemDisplay> =
                this@calculateInviteFriendsList.map { ItemDisplay.CoinItem(it) }.toMutableList()
            while (position < displayCoinList.size) {
                displayCoinList.add(position - 1, ItemDisplay.InviteFriends)
                position *= 2
            }
            return@withContext displayCoinList
        }

    fun onStateEventConsumed() {
        setState { copy(uiEvent = consumed()) }
    }
}
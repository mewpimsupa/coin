package com.pimsupa.coin.ui.coinlist

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.pimsupa.coin.domain.usecase.GetCoins
import com.pimsupa.coin.util.BaseViewModel
import com.pimsupa.coin.util.CoinDispatchers
import com.pimsupa.coin.util.CoinException
import com.pimsupa.coin.util.Dispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
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
    @Dispatcher(CoinDispatchers.Default) private val defaultDispatcher: CoroutineDispatcher
) :
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
            getCoins()
        }
    }

    private suspend fun getCoins() {
        getCoins.invoke(currentPage)
            .onStart {
                setState { copy(isLoading = true) }
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
            }
            .catch { exception ->
                when (exception) {
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
            .onCompletion {
                setState { copy(isLoading = false) }
            }
            .collect()
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
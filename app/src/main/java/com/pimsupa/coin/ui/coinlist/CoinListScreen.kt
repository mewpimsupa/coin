package com.pimsupa.coin.ui.coinlist

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pimsupa.coin.domain.model.Coin
import com.pimsupa.coin.ui.coinlist.component.CoinDetail
import com.pimsupa.coin.ui.coinlist.component.LandScapeUI
import com.pimsupa.coin.ui.coinlist.component.PortraitUI
import com.pimsupa.coin.util.LaunchedEventEffect
import com.pimsupa.coin.util.LocalCoinColor
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinListScreen(
    viewModel: CoinListViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState.collectAsState().value
    val onEvent = viewModel::onEvent

    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val openBottomSheet = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEventEffect(
        event = uiState.uiEvent,
        onConsumed = { viewModel.onStateEventConsumed() },
    ) {
        when (it) {
            is CoinListUiEvent.OpenCoinDetail -> {
                scope.launch {
                    openBottomSheet.value = true
                    bottomSheetState.show()
                }
            }

            is CoinListUiEvent.CloseCoinDetail -> {
                scope.launch {
                    openBottomSheet.value = false
                    bottomSheetState.hide()
                }
            }

            is CoinListUiEvent.OpenInviteFriends -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
                context.startActivity(intent)
            }
        }
    }



    CoinListScreenContent(uiState, onEvent)


    if (uiState.showCoinDetail != null)
        CoinDetail(
            coinDetail = uiState.showCoinDetail,
            onDismiss = { onEvent.invoke(CoinListEvent.OnDismissCoinDetail) },
            bottomSheetState = bottomSheetState
        )

    if (uiState.isFullScreenLoading) {
        Loading(modifier = Modifier.fillMaxSize())
    }
}


@OptIn(FlowPreview::class, ExperimentalMaterialApi::class)
@Composable
fun CoinListScreenContent(
    state: CoinListState,
    event: (CoinListEvent) -> Unit
) {
    val color = LocalCoinColor.current
    val listState = rememberLazyListState()
    val coins by rememberUpdatedState(newValue = state.filteredCoins)

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefresh,
        onRefresh = {
            event.invoke(CoinListEvent.RefreshCoins)
        }
    )
    LaunchedEffect(state.isLoading) {
        snapshotFlow {
            listState.layoutInfo.visibleItemsInfo
        }
            .debounce(300)
            .distinctUntilChanged()
            .collectLatest { visibleItems ->
                val lastVisibleItemIndex = visibleItems.lastOrNull()?.index ?: 0
                val totalItems = coins.size
                if (lastVisibleItemIndex >= totalItems - 1 && !state.isLoading && !state.isError) {
                    Log.d("test", "screen load more coins")
                    event(CoinListEvent.LoadMoreCoins)
                }
            }
    }


    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = color.background)
                .padding(padding)
                .pullRefresh(pullRefreshState)
        ) {

            when (getOrientation()) {
                Orientation.Portrait -> {
                    PortraitUI(
                        state = state,
                        event = event,
                        pullRefreshState = pullRefreshState,
                        listState = listState,
                        coins = coins
                    )
                }

                Orientation.Landscape -> {
                    LandScapeUI(
                        state = state,
                        event = event,
                        pullRefreshState = pullRefreshState,
                        listState = listState,
                        coins = coins
                    )
                }
            }


            PullRefreshIndicator(
                refreshing = state.isRefresh,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

enum class Orientation {
    Portrait, Landscape
}

@Composable
fun getOrientation(): Orientation {
    val configuration = LocalConfiguration.current
    return if (configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
        Orientation.Landscape
    } else {
        Orientation.Portrait
    }
}

@Composable
fun Loading(modifier: Modifier = Modifier) {
    val color = LocalCoinColor.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(40.dp),
            color = color.blue
        )
    }
}


@Composable
@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
fun CoinListScreenPreview() {
    CoinListScreenContent(
        state = CoinListState(
            coins = listOf(
                Coin(name = "test coin1", change = "-3.22"),
                Coin(name = "test coin2", change = "3.22"),
                Coin(name = "test coin2", change = "3.22"),
                Coin(name = "test coin2", change = "3.22")
            ),
        ), event = {}
    )
}


@Composable
@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
fun CoinListScreenErrorPreview() {
    CoinListScreenContent(
        state = CoinListState(
            isError = true,
            coins = listOf(
                Coin(name = "test coin1", change = "-3.22"),
                Coin(name = "test coin2", change = "3.22"),
                Coin(name = "test coin2", change = "3.22"),
                Coin(name = "test coin2", change = "3.22")
            )
        ), event = {}
    )
}



package com.pimsupa.coin.ui.coinlist

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pimsupa.coin.R
import com.pimsupa.coin.domain.model.Coin
import com.pimsupa.coin.ui.coinlist.component.CoinDetail
import com.pimsupa.coin.ui.coinlist.component.CoinItem
import com.pimsupa.coin.ui.coinlist.component.CoinListError
import com.pimsupa.coin.ui.coinlist.component.CoinListSearchNotFound
import com.pimsupa.coin.ui.coinlist.component.Top3Coin
import com.pimsupa.coin.ui.coinlist.component.Top3Text
import com.pimsupa.coin.util.LaunchedEventEffect
import com.pimsupa.coin.util.LocalCoinColor
import com.pimsupa.coin.util.LocalCoinTextStyle
import com.pimsupa.coin.util.toTextFieldValue
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

    val bottomSheetState = rememberModalBottomSheetState()
    val openBottomSheet = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

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
    val textStyle = LocalCoinTextStyle.current
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
                .padding(padding)
                .pullRefresh(pullRefreshState)
        ) {
            Column {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    value = state.searchText.value,
                    onValueChange = { text -> event.invoke(CoinListEvent.OnSearch(text)) },
                    leadingIcon = {
                        Image(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "icon search"
                        )
                    },
                    trailingIcon = {
                        if (state.searchText.value.text.isNotBlank()) {
                            Image(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(16.dp)
                                    .clickable {
                                        event.invoke(CoinListEvent.ClearSearchText)
                                    },
                                painter = painterResource(id = R.drawable.ic_clear),
                                contentDescription = "icon search"
                            )
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = color.lightGrey2)
                )
                HorizontalDivider()

                if (state.searchNotFound()) {
                    CoinListSearchNotFound()
                }
                LazyColumn(
                    modifier = Modifier.pullRefresh(pullRefreshState),
                    state = listState,
                ) {
                    item {
                        if(coins.isNotEmpty() && state.searchText.value.text.isEmpty()){
                            Top3Text(
                                modifier = Modifier.padding(
                                    top = 16.dp,
                                    start = 16.dp,
                                    end = 16.dp
                                )
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 12.dp, start = 15.dp, end = 15.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                state.getTop3Coins().forEach {
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(horizontal = 4.dp)
                                            .wrapContentHeight()
                                    ) {
                                        Top3Coin(coin = it)
                                    }
                                }
                            }
                        }

                    }
                    item {
                        Text(
                            modifier = Modifier.padding(vertical = 20.dp, horizontal = 16.dp),
                            text = stringResource(id = R.string.title_buy_sell_hold_crypto),
                            style = textStyle.titleBold,
                            color = color.allBlack
                        )
                    }
                    items(coins) { coin ->
                        CoinItem(coin) {
                            event.invoke(CoinListEvent.OnClickCoin(coin))
                        }
                    }
                    item {
                        if (state.isLoading && !state.isRefresh) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Loading()
                        }
                        if (state.isError) {
                            CoinListError {
                                event.invoke(CoinListEvent.OnClickLoadCoinsAgain)
                            }
                        }
                        Spacer(modifier = Modifier.height(64.dp))
                    }
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



package com.pimsupa.coin.ui.coinlist

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pimsupa.coin.R
import com.pimsupa.coin.domain.model.Coin
import com.pimsupa.coin.ui.coinlist.component.CoinDetail
import com.pimsupa.coin.ui.coinlist.component.CoinItem
import com.pimsupa.coin.util.LaunchedEventEffect
import com.pimsupa.coin.util.LocalCoinColor
import com.pimsupa.coin.util.LocalCoinTextStyle
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Local

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
            coin = uiState.showCoinDetail,
            onDismiss = { onEvent.invoke(CoinListEvent.OnDismissCoinDetail) },
            bottomSheetState = bottomSheetState
        )
}


@OptIn(FlowPreview::class)
@Composable
fun CoinListScreenContent(
    state: CoinListState,
    event: (CoinListEvent) -> Unit
) {
    val textStyle = LocalCoinTextStyle.current
    val color = LocalCoinColor.current
    val listState = rememberLazyListState()
    val coins by rememberUpdatedState(newValue = state.coins)


    LaunchedEffect(state.isLoading) {
        snapshotFlow {
            listState.layoutInfo.visibleItemsInfo
        }
            .debounce(300)
            .distinctUntilChanged()
            .collectLatest { visibleItems ->
                val lastVisibleItemIndex = visibleItems.lastOrNull()?.index ?: 0
                val totalItems = coins.size
                if (lastVisibleItemIndex >= totalItems - 1 && !state.isLoading) {
                    Log.d("test", "load more coins")
                    event(CoinListEvent.LoadCoins)
                }
            }
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState,
    ) {
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
            if (state.isLoading) {
                Spacer(modifier = Modifier.height(12.dp))
                Loading()
            }
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun Loading() {
    val color = LocalCoinColor.current
    Column(
        modifier = Modifier
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
                Coin(name = "test coin1"),
                Coin(name = "test coin2")
            )
        ), event = {}
    )
}


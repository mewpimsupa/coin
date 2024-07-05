package com.pimsupa.coin.ui.coinlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.pimsupa.coin.R
import com.pimsupa.coin.domain.model.Coin
import com.pimsupa.coin.util.LocalCoinTextStyle
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Local

@Composable
fun CoinListScreen(
    viewModel: CoinListViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState.collectAsState().value
    val onEvent = viewModel::onEvent
    CoinListScreenContent(uiState, onEvent)
}


@Composable
fun CoinListScreenContent(
    state: CoinListState,
    event: (CoinListEvent) -> Unit
) {
    val textStyle = LocalCoinTextStyle.current
    LazyColumn {
        item {
            Text(
                text = stringResource(id = R.string.title_buy_sell_hold_crypto),
                style = textStyle.titleBold
            )
        }
        items(state.coins) { coin ->
            // compose
            CoinItem(coin)
        }

    }
}


@Composable
fun CoinItem(coin: Coin) {
    val style = LocalCoinTextStyle.current
    Column {
        Text(text = coin.name, style = style.titleBold)
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

@Composable
@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
fun CoinItemPreview() {
    CoinItem(coin = Coin(name = "test coin"))
}
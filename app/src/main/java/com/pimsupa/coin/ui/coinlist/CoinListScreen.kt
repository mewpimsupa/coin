package com.pimsupa.coin.ui.coinlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pimsupa.coin.R
import com.pimsupa.coin.domain.model.Coin
import com.pimsupa.coin.ui.coinlist.component.CoinItem
import com.pimsupa.coin.util.LocalCoinColor
import com.pimsupa.coin.util.LocalCoinTextStyle

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
    val color = LocalCoinColor.current
    LazyColumn {
        item {
            Text(
                modifier = Modifier.padding(vertical = 20.dp, horizontal = 16.dp),
                text = stringResource(id = R.string.title_buy_sell_hold_crypto),
                style = textStyle.titleBold,
                color = color.allBlack
            )
        }
        item {
            if (state.isLoading) {
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
        }
        items(state.coins) { coin ->
            // compose
            CoinItem(coin)
        }

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


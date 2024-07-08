package com.pimsupa.coin.ui.coinlist.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pimsupa.coin.R
import com.pimsupa.coin.ui.coinlist.CoinListEvent
import com.pimsupa.coin.ui.coinlist.CoinListState
import com.pimsupa.coin.ui.coinlist.CoinListUiEvent
import com.pimsupa.coin.ui.coinlist.ItemDisplay
import com.pimsupa.coin.ui.coinlist.Loading
import com.pimsupa.coin.util.LocalCoinColor
import com.pimsupa.coin.util.LocalCoinTextStyle

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LandScapeUI(
    state: CoinListState,
    event: (CoinListEvent) -> Unit,
    pullRefreshState: PullRefreshState,
    listState: LazyListState,
    coins: List<ItemDisplay>
) {
    val textStyle = LocalCoinTextStyle.current
    val color = LocalCoinColor.current
    Column {
        SearchCoin(state = state, event = event)

        if (state.searchNotFound()) {
            CoinListSearchNotFound()
        }
        LazyColumn(
            modifier = Modifier.pullRefresh(pullRefreshState),
            state = listState,
        ) {
            item {
                if (coins.isNotEmpty() && state.searchText.value.text.isEmpty()) {
                    Top3Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 16.dp,
                                start = 16.dp,
                                end = 16.dp
                            ),
                        textAlign = TextAlign.Center
                    )
                    Row {
                        Spacer(modifier = Modifier.weight(1f))
                        Top3Layout(
                            modifier = Modifier
                                .weight(2f)
                                .fillMaxWidth(),
                            state = state,
                            event = event
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }

                }

            }
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp, horizontal = 16.dp),
                    text = stringResource(id = R.string.title_buy_sell_hold_crypto),
                    style = textStyle.titleBold,
                    color = color.textColor,
                    textAlign = TextAlign.Center
                )
            }

            item {
                coins.chunked(3).forEach { rowItems ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        rowItems.forEach { item ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                when (item) {
                                    is ItemDisplay.InviteFriends -> {
                                        InviteFriends {
                                            event.invoke(CoinListEvent.OnClickInviteFriends)
                                        }
                                    }
                                    is ItemDisplay.CoinItem -> {
                                        CoinItem(item.coin) {
                                            event.invoke(CoinListEvent.OnClickCoin(item.coin))
                                        }
                                    }
                                }
                            }
                        }
                        // Fill the remaining space if rowItems.size < columns
                        if (rowItems.size < 3) {
                            repeat(3 - rowItems.size) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
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
}


@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
fun LandScapeUIPreview() {
}
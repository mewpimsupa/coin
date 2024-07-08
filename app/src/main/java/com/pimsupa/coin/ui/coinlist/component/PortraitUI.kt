package com.pimsupa.coin.ui.coinlist.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pimsupa.coin.R
import com.pimsupa.coin.ui.coinlist.CoinListEvent
import com.pimsupa.coin.ui.coinlist.CoinListState
import com.pimsupa.coin.ui.coinlist.ItemDisplay
import com.pimsupa.coin.ui.coinlist.Loading
import com.pimsupa.coin.util.LocalCoinColor
import com.pimsupa.coin.util.LocalCoinTextStyle

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PortraitUI(
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
                        modifier = Modifier.padding(
                            top = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        )
                    )
                    Top3Layout(modifier = Modifier.fillMaxWidth(), state = state, event = event)
                }

            }
            item {
                Text(
                    modifier = Modifier
                        .padding(vertical = 20.dp, horizontal = 16.dp),
                    text = stringResource(id = R.string.title_buy_sell_hold_crypto),
                    style = textStyle.titleBold,
                    color = color.textColor
                )
            }
            items(coins) { filterCoin ->
                when (filterCoin) {
                    is ItemDisplay.InviteFriends -> {
                        InviteFriends {
                            event.invoke(CoinListEvent.OnClickInviteFriends)
                        }
                    }

                    is ItemDisplay.CoinItem -> {
                        CoinItem(filterCoin.coin) {
                            event.invoke(CoinListEvent.OnClickCoin(filterCoin.coin))
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
fun PortraitUIPreview() {
}
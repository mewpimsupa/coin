package com.pimsupa.coin.ui.coinlist.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pimsupa.coin.R
import com.pimsupa.coin.domain.model.Coin
import com.pimsupa.coin.ui.coinlist.CoinListEvent
import com.pimsupa.coin.ui.coinlist.CoinListState
import com.pimsupa.coin.util.CoinImage
import com.pimsupa.coin.util.LocalCoinColor
import com.pimsupa.coin.util.LocalCoinTextStyle


@Composable
fun Top3Layout(
    modifier: Modifier = Modifier,
    state: CoinListState,
    event: (CoinListEvent) -> Unit
) {
    val style = LocalCoinTextStyle.current
    val color = LocalCoinColor.current
    val context = LocalContext.current

    Row(
        modifier = modifier
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
                Top3Coin(coin = it) { coin ->
                    event.invoke(CoinListEvent.OnClickCoin(coin))
                }
            }
        }
    }
}

@Composable
@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
fun Top3LayoutPreview() {
    Top3Layout(
        state = CoinListState(
            coins = listOf( Coin(
                name = "test coin",
                iconUrl = "url",
                symbol = "test",
                lowVolume = true,
                price = "2034944.43434343",
                change = "23.3"
            ), Coin(
                name = "test coin",
                iconUrl = "url",
                symbol = "test",
                lowVolume = true,
                price = "2034944.43434343",
                change = "23.3"
            ), Coin(
                name = "test coin",
                iconUrl = "url",
                symbol = "test",
                lowVolume = true,
                price = "2034944.43434343",
                change = "23.3"
            )
            )
        ),
        event = {}
    )
}


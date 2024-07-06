package com.pimsupa.coin.ui.coinlist.component

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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pimsupa.coin.R
import com.pimsupa.coin.domain.model.Coin
import com.pimsupa.coin.util.CoinImage
import com.pimsupa.coin.util.LocalCoinColor
import com.pimsupa.coin.util.LocalCoinTextStyle
import com.pimsupa.coin.util.parseColor
import kotlinx.coroutines.launch
import org.jetbrains.annotations.Async

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetail(coin: Coin, onDismiss: () -> Unit, bottomSheetState: SheetState) {
    val textStyle = LocalCoinTextStyle.current
    val color = LocalCoinColor.current
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = bottomSheetState
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(top = 32.dp, start = 24.dp, end = 24.dp)
        ) {
            CoinHeader(coin)


        }
    }
}


@Composable
fun CoinHeader(coin: Coin) {
    val textStyle = LocalCoinTextStyle.current
    val color = LocalCoinColor.current
    Row {
        CoinImage(
            imageUrl = coin.iconUrl,
            desc = "coin icon",
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Row {
                Text(
                    text = coin.name,
                    style = textStyle.header2,
                    color = coin.color.parseColor()
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = coin.symbolDetail(),
                    style = textStyle.title,
                    color = color.allBlack
                )
            }
            Row {
                Text(
                    text = stringResource(id = R.string.title_price),
                    style = textStyle.detailBold1,
                    color = color.black
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = coin.getCoinDetailPrice(),
                    style = textStyle.detail1,
                    color = color.black
                )
            }
            Row {
                Text(
                    text = stringResource(id = R.string.title_market_cap),
                    style = textStyle.detailBold1,
                    color = color.black
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = coin.getCoinMarketCap(),
                    style = textStyle.detail1,
                    color = color.black
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
fun CoinDetailPreview() {
    CoinDetail(
        coin = Coin(
            name = "test",
            price = "1239204.320323",
            marketCap = "2320323",
            color = "#f7931A"
        ), onDismiss = { }, bottomSheetState = rememberModalBottomSheetState()
    )
}

@Composable
@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
fun CoinHeaderPreview() {
    CoinHeader(
        coin = Coin(
            name = "test",
            price = "1239204.320323",
            marketCap = "2320323",
            color = "#f7931A"
        )
    )
}

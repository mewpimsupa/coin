package com.pimsupa.coin.ui.coinlist.component

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pimsupa.coin.R
import com.pimsupa.coin.domain.model.Coin
import com.pimsupa.coin.domain.model.CoinDetail
import com.pimsupa.coin.util.CoinColor
import com.pimsupa.coin.util.CoinImage
import com.pimsupa.coin.util.LocalCoinColor
import com.pimsupa.coin.util.LocalCoinTextStyle
import com.pimsupa.coin.util.parseColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetail(coinDetail: CoinDetail, onDismiss: () -> Unit, bottomSheetState: SheetState) {

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = bottomSheetState
    ) {
        CoinContent(coinDetail)
    }
}

@Composable
fun CoinContent(coinDetail: CoinDetail) {
    val textStyle = LocalCoinTextStyle.current
    val color = LocalCoinColor.current
    val context = LocalContext.current
    Column(
        Modifier
            .fillMaxWidth()
            .heightIn(max = 565.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.padding(
                top = 32.dp,
                start = 24.dp,
                end = 24.dp
            )
        ) {
            CoinHeader(coinDetail)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = if (coinDetail.description.isBlank()) stringResource(id = R.string.text_no_description) else coinDetail.description,
                style = textStyle.detail2,
                color = color.grey
            )
            Spacer(modifier = Modifier.height(16.dp))

        }
        Spacer(modifier = Modifier.weight(1f))

        HorizontalDivider()
        TextButton(onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(coinDetail.websiteUrl))
            context.startActivity(intent)
        }) {
            Text(
                text = stringResource(id = R.string.button_go_to_website),
                textAlign = TextAlign.Center,
                style = textStyle.detailBold2,
                color = color.blue
            )
        }


    }
}

@Composable
fun CoinHeader(coinDetail: CoinDetail) {
    val textStyle = LocalCoinTextStyle.current
    val color = LocalCoinColor.current
    Row {
        CoinImage(
            imageUrl = coinDetail.iconUrl,
            desc = "coin icon",
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Row(verticalAlignment = Alignment.Top) {
                Text(
                    text = coinDetail.name,
                    style = textStyle.header2,
                    color = coinDetail.color.parseColor() ?: color.black
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = coinDetail.symbolDetail(),
                    style = textStyle.title,
                    color = color.allBlack
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(id = R.string.title_price),
                    style = textStyle.detailBold1,
                    color = color.black
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = coinDetail.getCoinDetailPrice(),
                    style = textStyle.detail1,
                    color = color.black
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(id = R.string.title_market_cap),
                    style = textStyle.detailBold1,
                    color = color.black
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = coinDetail.getCoinMarketCap(),
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
    CoinContent(
        coinDetail = CoinDetail(
            name = "test",
            price = "1239204.320323",
            marketCap = "2320323",
            color = "#f7931A",
            description = "12348290ewjkad;sadwndmsdksjdgshjadguwiasjdhwuakshdxjzchjzkhduwakhjsdkhuawkjshduwkajshduwkajsdhsadw"
        )
    )
}

@Composable
@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
fun CoinHeaderPreview() {
    CoinHeader(
        coinDetail = CoinDetail(
            name = "test",
            symbol = "TTC",
            price = "1239204.320323",
            marketCap = "2320323",
            color = "#f7931A"
        )
    )
}

@Composable
@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
fun CoinHeaderLongPreview() {
    //TODO fic the long header ui
    CoinHeader(
        coinDetail = CoinDetail(
            name = "reserfdfkdlsfkld;soekfld;soes;dlfkls;khjsdhf",
            symbol = "DDDDD",
            price = "1239204.320323",
            marketCap = "2320323",
            color = "#f7931A"
        )
    )
}


@Composable
@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
fun CoinDetailNoDescriptionPreview() {
    CoinContent(
        coinDetail = CoinDetail(
            name = "test",
            price = "1239204.320323",
            marketCap = "2320323",
            color = "#f7931A",
            description = ""
        )
    )
}



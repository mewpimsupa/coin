package com.pimsupa.coin.ui.coinlist.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
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
import com.pimsupa.coin.util.CoinImage
import com.pimsupa.coin.util.LocalCoinColor
import com.pimsupa.coin.util.LocalCoinTextStyle


@Composable
fun Top3Coin(coin: Coin, onClickCoinItem: (Coin) -> Unit = {}) {
    val style = LocalCoinTextStyle.current
    val color = LocalCoinColor.current
    val context = LocalContext.current

    Card(
        onClick = { onClickCoinItem.invoke(coin) },
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = color.card)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CoinImage(
                imageUrl = coin.iconUrl,
                desc = "coin icon",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = coin.symbol,
                style = style.titleBold,
                color = color.textColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = coin.name,
                style = style.title,
                color = color.textDetail,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            ChangeText(coin.isPositiveChange())
        }
    }
}

@Composable
@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
fun Top3CoinPreview() {
    Top3Coin(
        coin = Coin(
            name = "test coin",
            iconUrl = "url",
            symbol = "test",
            lowVolume = true,
            price = "2034944.43434343",
            change = "23.3"
        )
    )
}


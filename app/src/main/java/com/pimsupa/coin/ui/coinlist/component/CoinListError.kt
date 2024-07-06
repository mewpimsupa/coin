package com.pimsupa.coin.ui.coinlist.component

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pimsupa.coin.R
import com.pimsupa.coin.util.LocalCoinColor
import com.pimsupa.coin.util.LocalCoinTextStyle

@Composable
fun CoinListError(onClickTryAgain: () -> Unit = {}) {
    val textStyle = LocalCoinTextStyle.current
    val color = LocalCoinColor.current
    Column(
        modifier = Modifier
            .padding(vertical = 21.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.text_could_not_load_data),
            style = textStyle.title,
            color = color.black
        )
        TextButton(onClick = onClickTryAgain) {
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.button_try_again),
                textAlign = TextAlign.Center,
                style = textStyle.detailBold2,
                color = color.blue
            )
        }
    }
}


@Composable
@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
fun CoinListErrorPreview() {
    CoinListError()
}

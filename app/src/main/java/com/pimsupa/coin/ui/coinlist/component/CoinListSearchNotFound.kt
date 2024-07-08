package com.pimsupa.coin.ui.coinlist.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import dagger.Module


@Composable
fun CoinListSearchNotFound(modifier: Modifier = Modifier) {
    val textStyle = LocalCoinTextStyle.current
    val color = LocalCoinColor.current
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.text_not_found_title),
            style = textStyle.header1,
            color = color.textColor
        )
        Text(
            text = stringResource(id = R.string.text_not_found_sub_title),
            style = textStyle.title,
            color = color.textDetail
        )

    }
}


@Composable
@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
fun CoinListSearchNotFoundPreview() {
    CoinListSearchNotFound()
}

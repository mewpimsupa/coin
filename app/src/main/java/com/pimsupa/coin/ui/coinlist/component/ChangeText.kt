package com.pimsupa.coin.ui.coinlist.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pimsupa.coin.R
import com.pimsupa.coin.util.LocalCoinColor
import com.pimsupa.coin.util.LocalCoinTextStyle

@Composable
fun ChangeText(data: Pair<Boolean, String>) {
    val style = LocalCoinTextStyle.current
    val color = LocalCoinColor.current
    val (isPositive, change) = data
    Row(verticalAlignment = Alignment.CenterVertically) {
        if(change.isBlank()){
            Text(
                text = stringResource(id = R.string.text_no_data),
                style = style.detailBold1,
                color = color.black,
                textAlign = TextAlign.End
            )
        }else{
            Image(
                painter = if (isPositive)
                    painterResource(id = R.drawable.ic_arrow_up)
                else
                    painterResource(id = R.drawable.ic_arrow_down),
                contentDescription = "arrow change"
            )
            Spacer(modifier = Modifier.width(1.dp))
            Text(
                text = change,
                style = style.detailBold1,
                color = if (isPositive) color.green else color.red,
                textAlign = TextAlign.End
            )
        }

    }


}


@Composable
@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
fun ChangeTextUpPreview() {
    ChangeText(
        Pair(true, "3.04")
    )
}

@Composable
@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
fun ChangeTextDownPreview() {
    ChangeText(
        Pair(false, "3.04")
    )
}
package com.pimsupa.coin.ui.coinlist.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.pimsupa.coin.R
import com.pimsupa.coin.util.LocalCoinColor


@Composable
fun Top3Text(modifier: Modifier = Modifier, textAlign: TextAlign= TextAlign.Start) {
    val color = LocalCoinColor.current

    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = color.textColor,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        ) {
            append(stringResource(id = R.string.text_top))
        }

        withStyle(
            style = SpanStyle(
                color = color.highlight,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        ) {
            append(" " + stringResource(id = R.string.text_3))
        }

        withStyle(
            style = SpanStyle(
                color = color.textColor,
                fontWeight = FontWeight.W500,
                fontSize = 16.sp
            )
        ) {
            append(" " + stringResource(id = R.string.text_rank_crypto))
        }
    }

    Text(modifier = modifier, text = annotatedString, textAlign = textAlign)

}


@Composable
@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
fun Top3TextPreview() {
    Top3Text(modifier = Modifier)
}

package com.pimsupa.coin.ui.coinlist.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pimsupa.coin.R
import com.pimsupa.coin.ui.coinlist.CoinListEvent
import com.pimsupa.coin.ui.coinlist.CoinListState
import com.pimsupa.coin.util.LocalCoinColor
import com.pimsupa.coin.util.LocalCoinTextStyle

@Composable
fun SearchCoin(state: CoinListState, event: (CoinListEvent) -> Unit) {
    val style = LocalCoinTextStyle.current
    val color = LocalCoinColor.current
    val context = LocalContext.current

    androidx.compose.material3.OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        value = state.searchText.value,
        placeholder = {
            Text(
                text = stringResource(id = R.string.placeholder_search),
                color = color.textDetail,
                style = style.detail2,
            )
        },
        onValueChange = { text -> event.invoke(CoinListEvent.OnSearch(text)) },
        leadingIcon = {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "icon search",
                colorFilter = ColorFilter.tint(color = color.search)
            )
        },
        trailingIcon = {
            if (state.searchText.value.text.isNotBlank()) {
                Image(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(16.dp)
                        .clickable {
                            event.invoke(CoinListEvent.ClearSearchText)
                        },
                    painter = painterResource(id = R.drawable.ic_clear),
                    contentDescription = "icon search"
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = color.containerSearch,
            unfocusedContainerColor = color.containerSearch,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedTextColor = color.textColor,
            unfocusedTextColor = color.textColor,
            unfocusedPlaceholderColor = color.textDetail,
            focusedPlaceholderColor = color.textDetail
        )
    )
    HorizontalDivider()
}

@Composable
@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
fun SearchCoinPreview() {
    SearchCoin(
        state = CoinListState(),
        event = {}
    )
}
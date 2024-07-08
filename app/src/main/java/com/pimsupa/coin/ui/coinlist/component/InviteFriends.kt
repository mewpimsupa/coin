package com.pimsupa.coin.ui.coinlist.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pimsupa.coin.R
import com.pimsupa.coin.ui.coinlist.Orientation
import com.pimsupa.coin.ui.coinlist.getOrientation
import com.pimsupa.coin.util.LocalCoinColor


@Composable
fun InviteFriends(modifier: Modifier = Modifier, onClickInviteFriends: () -> Unit = {}) {
    val color = LocalCoinColor.current
    val textStyle = LocalTextStyle.current

    Card(
        onClick = onClickInviteFriends,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = color.inviteCard)
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 21.dp
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_invite_friends),
                modifier = Modifier.size(40.dp), contentDescription = "icon invite friends"
            )
            Spacer(modifier = Modifier.width(16.dp))
            InviteText()
        }
    }
}

@Composable
fun InviteText() {
    val color = LocalCoinColor.current
    val textStyle = LocalTextStyle.current
    val orientation = getOrientation()
    val textSize = if (orientation == Orientation.Portrait) {
        16.sp
    } else {
        12.sp
    }

    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = color.textInviteColor,
                fontWeight = FontWeight.W400,
                fontSize = textSize
            )
        ) {
            append(stringResource(id = R.string.text_earn_free))
        }

        withStyle(
            style = SpanStyle(
                color = color.blue,
                fontWeight = FontWeight.Bold,
                fontSize = textSize
            )
        ) {
            append(" " + stringResource(id = R.string.text_invite_friend))
        }
    }

    Text(text = annotatedString)

}

@Composable
@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
fun InviteFriendsPreview() {
    InviteFriends()
}

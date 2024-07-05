package com.pimsupa.coin.ui.coinlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CoinListScreen(
    viewModel: CoinListViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState.collectAsState().value
    val onEvent = viewModel::onEvent
    CoinListScreenContent()
}


@Composable
fun CoinListScreenContent() {
    Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "test ka")
    }
}


@Composable
@Preview
fun CoinListScreenPreview() {
    CoinListScreen()
}
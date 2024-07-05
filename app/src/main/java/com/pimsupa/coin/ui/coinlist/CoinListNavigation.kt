package com.pimsupa.coin.ui.coinlist

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable


const val coinListRoute = "coin_list_route"

fun NavController.navigateToCoinList(navOptions: NavOptions? = null) {
    this.navigate(coinListRoute, navOptions)
}

fun NavGraphBuilder.coinListScreen(
) {
    composable(route = coinListRoute) {
        CoinListScreen()
    }
}


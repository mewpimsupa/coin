package com.pimsupa.coin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.pimsupa.coin.ui.coinlist.coinListRoute
import com.pimsupa.coin.ui.coinlist.coinListScreen

@Composable
fun CoinNavigation(navController: NavHostController, startDestination: String = coinListRoute) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        coinListScreen()
    }

}
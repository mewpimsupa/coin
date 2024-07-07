package com.pimsupa.coin.domain.repository

import com.pimsupa.coin.domain.model.Coin
import com.pimsupa.coin.domain.model.CoinDetail
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    fun getCoins(page: Int): Flow<List<Coin>>
    fun getCoinDetail(uuid: String): Flow<CoinDetail>
    fun searchCoin(keyword: String): Flow<List<Coin>>
}
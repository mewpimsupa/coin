package com.pimsupa.coin.domain.repository

import com.pimsupa.coin.domain.model.Coin
import com.pimsupa.coin.domain.model.CoinDetail
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    suspend fun getCoinCount(): Int
    fun getLocalCoins(limit: Int, offset: Int): Flow<List<Coin>>
    fun getCoins(page: Int): Flow<List<Coin>>
    fun getCoinDetail(uuid: String): Flow<CoinDetail>
    fun searchCoin(keyword: String): Flow<List<Coin>>
    fun updateCoins(limit: Int): Flow<List<Coin>>
    suspend fun updateLocalCoins(list:List<Coin>)
    fun getAllLocalCoin(): Flow<List<Coin>>
}
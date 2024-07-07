package com.pimsupa.coin.data.repository

import com.pimsupa.coin.data.remote.CoinApi
import com.pimsupa.coin.data.mapper.toCoin
import com.pimsupa.coin.data.mapper.toCoinDetail
import com.pimsupa.coin.domain.model.Coin
import com.pimsupa.coin.domain.model.CoinDetail
import com.pimsupa.coin.domain.repository.CoinRepository
import com.pimsupa.coin.util.CoinException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(private val api: CoinApi) : CoinRepository {
    override fun getCoins(page: Int): Flow<List<Coin>> = flow {
        try {
            val response = api.getCoins(limit = 20, offset = page * 20)
            if (response.isSuccessful) {
                val data =
                    response.body()?.data ?: throw CoinException.CoinIsNullException()
                emit(data.coins.map { it.toCoin() })
            } else {
                throw CoinException.GetCoinsErrorException()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getCoinDetail(uuid: String): Flow<CoinDetail> = flow {
        try {
            val response = api.getCoinDetail(uuid = uuid)
            if (response.isSuccessful) {
                val data =
                    response.body()?.data ?: throw CoinException.GetCoinDetailErrorException()
                emit(data.coin.toCoinDetail())
            } else {
                throw CoinException.GetCoinDetailErrorException()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override fun searchCoin(keyword: String): Flow<List<Coin>> = flow {
        try {
            val response = api.searchCoins(keyword)
            if (response.isSuccessful) {
                val data =
                    response.body()?.data ?: throw CoinException.CoinIsNullException()
                emit(data.coins.map { it.toCoin() })
            } else {
                throw CoinException.GetCoinsErrorException()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override fun updateCoin(limit: Int): Flow<List<Coin>> = flow {
        try {
            val response = api.getCoins(limit = limit, offset = null)
            if (response.isSuccessful) {
                val data =
                    response.body()?.data ?: throw CoinException.CoinIsNullException()
                emit(data.coins.map { it.toCoin() })
            } else {
                throw CoinException.GetCoinsErrorException()
            }
        } catch (e: Exception) {
            throw e
        }
    }

}
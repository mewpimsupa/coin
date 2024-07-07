package com.pimsupa.coin.data.repository

import android.util.Log
import com.pimsupa.coin.data.local.CoinDatabase
import com.pimsupa.coin.data.remote.CoinApi
import com.pimsupa.coin.data.mapper.toCoin
import com.pimsupa.coin.data.mapper.toCoinDetail
import com.pimsupa.coin.data.mapper.toCoinEntity
import com.pimsupa.coin.domain.model.Coin
import com.pimsupa.coin.domain.model.CoinDetail
import com.pimsupa.coin.domain.repository.CoinRepository
import com.pimsupa.coin.util.CoinException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinApi,
    private val db: CoinDatabase
) : CoinRepository {
    override suspend fun getCoinCount(): Int {
        return db.coinDao().getCoinCount()
    }

    override fun getLocalCoins(limit: Int, offset: Int): Flow<List<Coin>> {
        return db.coinDao().getCoinList(limit, offset).map { entities ->
            entities.map { it.toCoin() }
        }
    }

    override fun getCoins(page: Int): Flow<List<Coin>> =
        flow {
            try {
                val response = api.getCoins(limit = 20, offset = page * 20)
                if (response.isSuccessful) {
                    val data =
                        response.body()?.data ?: throw CoinException.CoinIsNullException()
                    saveToDatabase(data.coins)
                    emit(data.coins.map { it.toCoin() })
                } else {
                    throw CoinException.GetCoinsErrorException()
                }
            } catch (e: Exception) {
                throw e
            }
        }

    private suspend fun saveToDatabase(coinList: List<com.pimsupa.coin.data.remote.getcoins.Coin>) {
        db.coinDao().insertCoins(coinList.map { it.toCoinEntity() })
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

    override fun updateCoins(limit: Int): Flow<List<Coin>> = flow {
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

    override suspend fun updateLocalCoins(list: List<Coin>) {
        db.coinDao().insertCoins(list.map { it.toCoinEntity() })
    }

}
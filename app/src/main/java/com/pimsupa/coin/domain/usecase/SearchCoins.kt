package com.pimsupa.coin.domain.usecase

import com.pimsupa.coin.data.CoinApi
import com.pimsupa.coin.data.mapper.toCoin
import com.pimsupa.coin.domain.model.Coin
import com.pimsupa.coin.util.CoinDispatchers
import com.pimsupa.coin.util.CoinException
import com.pimsupa.coin.util.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface SearchCoins {
    operator fun invoke(keyword: String): Flow<List<Coin>>
}

class SearchCoinsImpl @Inject constructor(
    private val api: CoinApi,
    @Dispatcher(CoinDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : SearchCoins {
    override fun invoke(keyword: String): Flow<List<Coin>> = flow {
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
    }.flowOn(ioDispatcher)

}
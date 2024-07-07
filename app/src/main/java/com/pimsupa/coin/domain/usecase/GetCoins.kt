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
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetCoins {
    operator fun invoke(page: Int): Flow<List<Coin>>
}

class GetCoinsImpl @Inject constructor(
    private val api: CoinApi,
    @Dispatcher(CoinDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : GetCoins {
    override fun invoke(page: Int): Flow<List<Coin>> = flow {
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
    }.flowOn(ioDispatcher)

}
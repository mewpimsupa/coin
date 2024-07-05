package com.pimsupa.coin.domain.usecase

import com.pimsupa.coin.data.CoinApi
import com.pimsupa.coin.data.mapper.toCoin
import com.pimsupa.coin.domain.model.Coin
import com.pimsupa.coin.util.CoinDispatchers
import com.pimsupa.coin.util.CoinException
import com.pimsupa.coin.util.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetCoins {
    suspend operator fun invoke(): Result<List<Coin>>
}

class GetCoinsImpl @Inject constructor(
    private val api: CoinApi,
    @Dispatcher(CoinDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : GetCoins {
    override suspend fun invoke(): Result<List<Coin>> = withContext(ioDispatcher) {
        val response = api.getCoins(scopeLimit = 20)
        return@withContext if (response.isSuccessful) {
            val data =
                response.body()?.data ?: return@withContext Result.failure(CoinException.CoinIsNullException())
            Result.success(data.coins.map { it.toCoin() })
        } else {
            Result.failure(CoinException.GetCoinsErrorException())
        }
    }

}
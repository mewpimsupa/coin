package com.pimsupa.coin.domain.usecase

import com.pimsupa.coin.data.CoinApi
import com.pimsupa.coin.data.mapper.toCoin
import com.pimsupa.coin.domain.model.Coin
import com.pimsupa.coin.util.CoinException
import javax.inject.Inject

interface GetCoins {
    suspend operator fun invoke(): Result<List<Coin>>
}

class GetCoinsImpl @Inject constructor(val api: CoinApi) : GetCoins {
    override suspend fun invoke(): Result<List<Coin>> {
        val response = api.getCoins(scopeLimit = 20)
        return if (response.isSuccessful) {
            val data =
                response.body()?.data ?: return Result.failure(CoinException.CoinIsNullException())
            Result.success(data.coins.map { it.toCoin() })
        } else {
            Result.failure(CoinException.GetCoinsErrorException())
        }
    }

}
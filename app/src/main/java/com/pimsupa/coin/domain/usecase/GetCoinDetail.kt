package com.pimsupa.coin.domain.usecase

import com.pimsupa.coin.data.CoinApi
import com.pimsupa.coin.data.mapper.toCoin
import com.pimsupa.coin.data.mapper.toCoinDetail
import com.pimsupa.coin.domain.model.Coin
import com.pimsupa.coin.domain.model.CoinDetail
import com.pimsupa.coin.util.CoinDispatchers
import com.pimsupa.coin.util.CoinException
import com.pimsupa.coin.util.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface GetCoinDetail {
    operator fun invoke(uuid: String): Flow<CoinDetail>
}

class GetCoinDetailImpl @Inject constructor(
    private val api: CoinApi,
    @Dispatcher(CoinDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : GetCoinDetail {
    override fun invoke(uuid: String): Flow<CoinDetail> = flow {
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
    }.flowOn(ioDispatcher)

}
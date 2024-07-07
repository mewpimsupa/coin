package com.pimsupa.coin.domain.usecase

import com.pimsupa.coin.domain.model.Coin
import com.pimsupa.coin.domain.repository.CoinRepository
import com.pimsupa.coin.util.CoinDispatchers
import com.pimsupa.coin.util.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface GetCoins {
    operator fun invoke(page: Int): Flow<List<Coin>>
}

class GetCoinsImpl @Inject constructor(
    private val repository: CoinRepository,
    @Dispatcher(CoinDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : GetCoins {
    override fun invoke(page: Int): Flow<List<Coin>> = flow {
        val limit = 20
        val offset = page * limit

        // Fetch local coins with the specified limit and offset
        val localCoins = repository.getLocalCoins(limit, offset).first()

        if (localCoins.size < limit) {
            // If local data is insufficient, fetch from the API
            emitAll(repository.getCoins(page))
        } else {
            // Otherwise, emit the local data
            emit(localCoins)
        }
    }.flowOn(ioDispatcher)

}


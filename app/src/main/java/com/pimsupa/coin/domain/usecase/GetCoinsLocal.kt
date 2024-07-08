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

interface GetCoinsLocal {
    operator fun invoke(): Flow<List<Coin>>
}

class GetCoinsLocalImpl @Inject constructor(
    private val repository: CoinRepository,
) : GetCoinsLocal {
    override fun invoke(): Flow<List<Coin>> {
        return repository.getAllLocalCoin()
    }


}


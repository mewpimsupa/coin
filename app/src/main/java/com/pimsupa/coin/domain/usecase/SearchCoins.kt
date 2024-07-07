package com.pimsupa.coin.domain.usecase

import com.pimsupa.coin.domain.model.Coin
import com.pimsupa.coin.domain.repository.CoinRepository
import com.pimsupa.coin.util.CoinDispatchers
import com.pimsupa.coin.util.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface SearchCoins {
    operator fun invoke(keyword: String): Flow<List<Coin>>
}

class SearchCoinsImpl @Inject constructor(
    private val repository: CoinRepository,
    @Dispatcher(CoinDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : SearchCoins {
    override fun invoke(keyword: String): Flow<List<Coin>> =
        repository.searchCoin(keyword).flowOn(ioDispatcher)

}
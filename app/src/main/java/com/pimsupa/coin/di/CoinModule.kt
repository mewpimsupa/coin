package com.pimsupa.coin.di

import com.pimsupa.coin.data.repository.CoinRepositoryImpl
import com.pimsupa.coin.domain.repository.CoinRepository
import com.pimsupa.coin.domain.usecase.GetCoinDetail
import com.pimsupa.coin.domain.usecase.GetCoinDetailImpl
import com.pimsupa.coin.domain.usecase.GetCoins
import com.pimsupa.coin.domain.usecase.GetCoinsImpl
import com.pimsupa.coin.domain.usecase.SearchCoins
import com.pimsupa.coin.domain.usecase.SearchCoinsImpl
import com.pimsupa.coin.util.CoinDispatchers
import com.pimsupa.coin.util.Dispatcher
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class CoinModule {

    @Binds
    abstract fun bindsGetCoins(
        useCaseImpl: GetCoinsImpl,
    ): GetCoins

    @Binds
    abstract fun bindsGetCoinDetail(
        useCaseImpl: GetCoinDetailImpl,
    ): GetCoinDetail

    @Binds
    abstract fun bindsSearchCoins(
        useCaseImpl: SearchCoinsImpl,
    ): SearchCoins
    @Binds
    abstract fun bindsCoinRepository(
        useCaseImpl: CoinRepositoryImpl,
    ): CoinRepository
}


@Module
@InstallIn(SingletonComponent::class)
object CoinSingletonModule {
    @Provides
    @Dispatcher(CoinDispatchers.IO)
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Dispatcher(CoinDispatchers.Default)
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Dispatcher(CoinDispatchers.Main)
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Dispatcher(CoinDispatchers.Unconfined)
    fun providesUnconfinedDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined
}
package com.pimsupa.coin.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.pimsupa.coin.data.local.CoinDatabase
import com.pimsupa.coin.data.remote.CoinApi
import com.pimsupa.coin.data.repository.CoinRepositoryImpl
import com.pimsupa.coin.domain.repository.CoinRepository
import com.pimsupa.coin.domain.usecase.GetCoinDetail
import com.pimsupa.coin.domain.usecase.GetCoinDetailImpl
import com.pimsupa.coin.domain.usecase.GetCoins
import com.pimsupa.coin.domain.usecase.GetCoinsImpl
import com.pimsupa.coin.domain.usecase.SearchCoins
import com.pimsupa.coin.domain.usecase.SearchCoinsImpl
import com.pimsupa.coin.domain.usecase.UpdateCoin
import com.pimsupa.coin.domain.usecase.UpdateCoinImpl
import com.pimsupa.coin.util.CoinDispatchers
import com.pimsupa.coin.util.Dispatcher
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
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
    abstract fun bindsUpdateCoin(
        useCaseImpl: UpdateCoinImpl,
    ): UpdateCoin
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

    @Provides
    fun providesCoinRepository(coinApi: CoinApi): CoinRepository = CoinRepositoryImpl(api = coinApi)

    @Provides
    @Singleton
    fun provideWorkManager(
        @ApplicationContext context: Context
    ): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideMasterDatabase(app: Application): CoinDatabase {
        return Room.databaseBuilder(
            app,
            CoinDatabase::class.java,
            "coin_db",
        ).fallbackToDestructiveMigration().build()
    }
}
package com.pimsupa.coin.di

import com.pimsupa.coin.domain.usecase.GetCoins
import com.pimsupa.coin.domain.usecase.GetCoinsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class CoinModule {
    @Binds
    abstract fun bindsGetCoins(
        useCaseImpl: GetCoinsImpl,
    ): GetCoins

}
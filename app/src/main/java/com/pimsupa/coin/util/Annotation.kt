package com.pimsupa.coin.util

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: CoinDispatchers)

enum class CoinDispatchers {
    Unconfined,
    Default,
    IO,
    Main,
}

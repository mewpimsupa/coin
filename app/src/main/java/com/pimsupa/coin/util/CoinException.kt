package com.pimsupa.coin.util

sealed class CoinException(override val message: String?) : Exception() {
    data class CoinIsNullException(override val message: String? = "coin is null") : CoinException(message)
    data class GetCoinsErrorException(override val message: String? = "get coins is error") : CoinException(message)
    data class GetCoinDetailErrorException(override val message: String? = "get coin detial is error") : CoinException(message)

}
package com.pimsupa.coin.data

import com.pimsupa.coin.data.getcoins.CoinsResponse
import com.pimsupa.coin.util.BaseResponse
import retrofit2.Response
import retrofit2.http.GET

interface CoinApi {

    @GET("/coins")
    suspend fun getCoins(): Response<BaseResponse<CoinsResponse>>

}
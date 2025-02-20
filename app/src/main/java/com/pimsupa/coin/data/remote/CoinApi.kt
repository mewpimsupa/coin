package com.pimsupa.coin.data.remote

import com.pimsupa.coin.data.remote.coindetail.CoinDetailResponse
import com.pimsupa.coin.data.remote.getcoins.CoinsResponse
import com.pimsupa.coin.util.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinApi {

    @GET("coins")
    suspend fun getCoins(
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?
    ): Response<BaseResponse<CoinsResponse>>

    @GET("coin/{uuid}")
    suspend fun getCoinDetail(
        @Path("uuid") uuid: String,
    ): Response<BaseResponse<CoinDetailResponse>>

    @GET("coins")
    suspend fun searchCoins(
        @Query("search") keyword: String,
    ): Response<BaseResponse<CoinsResponse>>

}
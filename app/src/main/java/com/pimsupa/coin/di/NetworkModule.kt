package com.pimsupa.coin.di

import com.pimsupa.coin.data.remote.AuthAuthenticator
import com.pimsupa.coin.data.remote.CoinApi
import com.pimsupa.coin.util.Configs
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideOkHttpClientBuilder(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authAuthenticator: AuthAuthenticator
    ): OkHttpClient.Builder =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .authenticator(authAuthenticator)


    @Provides
    @Singleton
    fun provideOkHttpClient(okHttpClientBuilder: OkHttpClient.Builder): OkHttpClient {
        return okHttpClientBuilder
            .connectTimeout(Configs.CONNECTION_TIMEOUT_DEFAULT_MS, TimeUnit.MILLISECONDS)
            .readTimeout(Configs.CONNECTION_TIMEOUT_DEFAULT_MS, TimeUnit.MILLISECONDS)
            .writeTimeout(Configs.CONNECTION_TIMEOUT_DEFAULT_MS, TimeUnit.MILLISECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Configs.API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideCoinApi(retrofit: Retrofit): CoinApi {
        return retrofit
            .create(CoinApi::class.java)
    }
}
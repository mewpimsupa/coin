package com.pimsupa.coin.data

import android.util.Log
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthAuthenticator @Inject constructor() : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            val newToken = "coinranking07393fe27059f5d9286100a7f969e4fb1cd479075c81d56e"

            response.request.newBuilder()
                .header("x-access-token", newToken)
                .build()
        }
    }
}

package com.pimsupa.coin.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class RateLimitInterceptor(private val maxRequests: Int, private val timeWindowMillis: Long) :
    Interceptor {

    private val requestTimestamps = mutableListOf<Long>()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        synchronized(requestTimestamps) {
            val currentTime = System.currentTimeMillis()
            requestTimestamps.removeAll { it < currentTime - timeWindowMillis }

            if (requestTimestamps.size >= maxRequests) {
                throw IOException("Rate limit exceeded. Too many requests in the given time frame.")
            }

            requestTimestamps.add(currentTime)
        }

        return chain.proceed(chain.request())
    }
}
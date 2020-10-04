package com.test.newsappoffline.net

import okhttp3.Interceptor
import okhttp3.Response

abstract class ConnectionInterceptor : Interceptor {

    abstract fun isInternetAvailable(): Boolean
    abstract fun onInternetUnavailable()
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        if (!isInternetAvailable()) {
            onInternetUnavailable()
        }
        return chain.proceed(request)
    }
}
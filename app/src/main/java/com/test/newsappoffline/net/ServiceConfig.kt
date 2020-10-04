package com.test.newsappoffline.net

import com.google.gson.Gson
import com.test.newsappoffline.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceConfig private constructor() {
    companion object {
        public val api: Api by lazy {
            createApiService()
        }
        /* private var api: Api? = null
         fun getApiService(): Api = api ?: synchronized(this) {
             createApiService().also {
                 api = it
             }

         }*/

        private fun createApiService(): Api {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).addInterceptor(object : ConnectionInterceptor() {
                override fun isInternetAvailable(): Boolean {
                    return true
                }

                override fun onInternetUnavailable() {

                }
            })

            val retrofit =
                Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(Gson())).client(httpClient.build()).baseUrl(BuildConfig.BASE_URL)
                    .build()
            return retrofit.create(Api::class.java)


        }

    }
}
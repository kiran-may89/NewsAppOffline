package com.test.newsappoffline.net

import com.test.newsappoffline.BuildConfig
import com.test.newsappoffline.models.news.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("page")
        page: Int = 1,
        @Query("country")
        country: String = "us",
        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY,
    ):Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchNews(
        @Query("q")
        search:String,
        @Query("page")
        page: Int = 1,

        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY,
    ):Response<NewsResponse>
}
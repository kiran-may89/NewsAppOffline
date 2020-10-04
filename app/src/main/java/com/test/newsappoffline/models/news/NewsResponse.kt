package com.test.newsappoffline.models.news

import com.test.newsappoffline.models.news.Article

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)
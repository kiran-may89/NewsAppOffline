package com.test.newsappoffline.repository

import com.test.newsappoffline.database.DataBase
import com.test.newsappoffline.models.news.Article
import com.test.newsappoffline.net.Api

class NewsRepository(private val db:DataBase,private val api:Api) {
    suspend fun getBreakNews(countryCode:String,page:Int)=  api.getBreakingNews(page,countryCode)
    suspend fun searchNews(search:String,page:Int)=  api.searchNews(search,page = page)
    suspend fun  saveArticle(artile:Article) = db.articleDao().insertItem(artile)
    fun getSavedArticles() = db.articleDao().getArticles()
    suspend fun deleteArticle(article:Article) = db.articleDao().deleteArticles(article)
}
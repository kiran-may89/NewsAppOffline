package com.test.newsappoffline.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.test.newsappoffline.models.news.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<Article>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(article: Article)

    @Query("SELECT * FROM article")
    fun getArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticles(article: Article)


}
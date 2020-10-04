package com.test.newsappoffline.database

import android.content.Context
import androidx.room.*
import com.test.newsappoffline.database.converters.Converters
import com.test.newsappoffline.database.dao.ArticleDao
import com.test.newsappoffline.models.news.Article

@Database(entities = [Article::class],version = 1)
@TypeConverters(Converters::class)
abstract class DataBase :RoomDatabase() {
    abstract fun articleDao():ArticleDao

    companion object{
        @Volatile
        private var database:DataBase?= null
        private val lock = Any()
        operator fun invoke(context: Context)= database?: synchronized(lock){
            database?:createDataBase(context).also{
                database  = it;
            }
        }
        private fun createDataBase(context: Context) = Room.databaseBuilder(context,DataBase::class.java,"news.db").build()
    }
}
package com.test.newsappoffline

import android.content.Context
import com.test.newsappoffline.database.DataBase
import com.test.newsappoffline.database.dao.ArticleDao
import com.test.newsappoffline.net.ServiceConfig
import com.test.newsappoffline.repository.NewsRepository
import com.test.newsappoffline.ui.viewmodel.ViewModelFactory

class Injection {
    companion object{
        fun providesDataBase(context: Context): DataBase {
            return DataBase(context)
        }
        fun providesNewsRepository(context: Context):NewsRepository{
            return NewsRepository(providesDataBase(context),ServiceConfig.api)
        }
        fun  providesMainFactory(context: Context):ViewModelFactory{
            return ViewModelFactory(providesNewsRepository(context))
        }
    }
}
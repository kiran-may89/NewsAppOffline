package com.test.newsappoffline.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.newsappoffline.repository.NewsRepository
import java.lang.IllegalArgumentException

class ViewModelFactory(private val repos:NewsRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       if(modelClass.isAssignableFrom(MainViewModel::class.java)){
           return MainViewModel(repos) as T
       }else{
           throw IllegalArgumentException("View Model Not Found")
       }
    }
}
package com.test.newsappoffline.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.newsappoffline.models.news.Article
import com.test.newsappoffline.models.news.NewsResponse
import com.test.newsappoffline.net.ApiResources
import com.test.newsappoffline.repository.NewsRepository
import kotlinx.coroutines.launch

class MainViewModel(val repo: NewsRepository) : ViewModel() {
    private val breakingNewsMutableLiveData: MutableLiveData<ApiResources<NewsResponse>> = MutableLiveData()
    val breakNewsLiveData: LiveData<ApiResources<NewsResponse>> get() = breakingNewsMutableLiveData
    private val searchNewsMutableLiveData: MutableLiveData<ApiResources<NewsResponse>> = MutableLiveData()
    val searchNewsLiveData: LiveData<ApiResources<NewsResponse>> get() = searchNewsMutableLiveData
    private var breakingNewsResponse: NewsResponse? = null
     var breakingNewsPage = 1;
    private var searchNewsPage = 1;
    fun searchNews(searchTerm: String) = viewModelScope.launch {
        val response = repo.searchNews(searchTerm, searchNewsPage)
        if (response.isSuccessful) {
            response.body()?.let {
                searchNewsMutableLiveData.postValue(ApiResources.Success(it, response.message()))
            }
        } else {
            searchNewsMutableLiveData.postValue(ApiResources.Failed(response.message()))
        }

    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        val response = repo.getBreakNews(countryCode, breakingNewsPage)
        if (response.isSuccessful) {
            response.body()?.let {
                breakingNewsPage++
                breakingNewsMutableLiveData.value?.data?.let { oldArticles ->
                    it.articles.addAll(oldArticles.articles)
                }

                breakingNewsMutableLiveData.postValue(ApiResources.Success(it, response.message()))
            }
        } else {
            breakingNewsMutableLiveData.postValue(ApiResources.Failed(response.message()))
        }

    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        repo.saveArticle(article)
    }

    fun getSavedNews() = repo.getSavedArticles()
    fun delteArticle(article: Article) = viewModelScope.launch {
        repo.deleteArticle(article)
    }
}
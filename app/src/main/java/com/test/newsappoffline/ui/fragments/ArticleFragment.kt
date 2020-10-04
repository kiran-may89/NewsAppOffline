package com.test.newsappoffline.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.test.newsappoffline.Injection
import com.test.newsappoffline.R
import com.test.newsappoffline.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment:Fragment(){

val args:ArticleFragmentArgs by navArgs()

    private val mainViewModel: MainViewModel by lazy {

        ViewModelProvider(requireActivity(), Injection.providesMainFactory(requireContext()))
            .get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_article,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article = args.article
        webView.apply {
            webViewClient  = WebViewClient()
            loadUrl(article.url)
        }
        fab.setOnClickListener{
            mainViewModel.saveArticle(article)
            Snackbar.make(fab,"News Saved SuccessFully", Snackbar.LENGTH_LONG).show()
        }
    }

}
package com.test.newsappoffline.ui.fragments

import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.test.newsappoffline.Injection
import com.test.newsappoffline.R
import com.test.newsappoffline.models.news.NewsResponse
import com.test.newsappoffline.net.ApiResources
import com.test.newsappoffline.ui.adapters.NewsAdapter
import com.test.newsappoffline.ui.viewmodel.MainViewModel
import com.test.newsappoffline.utils.RecyclerViewTouchListener
import kotlinx.android.synthetic.main.fragment_search_news.*

import kotlinx.android.synthetic.main.fragment_search_news.paginationProgressBar
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter()
    }

    private val mainViewModel: MainViewModel by lazy {

        ViewModelProvider(requireActivity(), Injection.providesMainFactory(requireContext())).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var job: Job? = null
        etSearch.addTextChangedListener {
            job?.cancel()
            job = MainScope().launch {
                delay(5_000L)
               it?.let {chars->
                   if(chars.toString().isNotEmpty()){
                       mainViewModel.searchNews(chars.toString())
                   }
               }
            }

        }
        rvSearchNews.apply {
            layoutManager = LinearLayoutManager(this@SearchFragment.requireContext(), RecyclerView.VERTICAL, false)
            adapter = newsAdapter
            addOnItemTouchListener(RecyclerViewTouchListener(this@SearchFragment.requireContext(),this) {
               val item =  newsAdapter.differ.currentList[it]
                val bundle  = Bundle()
                bundle.putParcelable("Article",item)
                findNavController().navigate(R.id.action_searchFragment_to_articleFragment,bundle)

            })


        }

        mainViewModel.searchNewsLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ApiResources.Loading -> showHideProgressBar(true)
                is ApiResources.Success -> {
                    it.data?.let { response ->
                        updateAdapter(response)
                    }
                }
                is ApiResources.Failed -> showErrorMsg()
            }
        }
    }

    fun showErrorMsg() {
        showHideProgressBar(false)
        Snackbar.make(paginationProgressBar, "SomeThingWentWrong", Snackbar.LENGTH_LONG).show()
    }

    fun updateAdapter(response: NewsResponse) {
        showHideProgressBar(false)
        newsAdapter.differ.submitList(response.articles)

    }

    fun showHideProgressBar(visibility: Boolean) {
        paginationProgressBar.visibility = if (visibility) View.VISIBLE else View.GONE

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDetach() {
        super.onDetach()
    }
}
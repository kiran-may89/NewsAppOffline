package com.test.newsappoffline.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.AbsListView
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
import kotlinx.android.synthetic.main.fragment_breaking_news.*


class BreakingNewsFragment: Fragment() {

    private val newsAdapter:NewsAdapter by lazy {
        NewsAdapter()
    }
    private val mainViewModel: MainViewModel by lazy {

        ViewModelProvider(requireActivity(), Injection.providesMainFactory(requireContext()))
            .get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_breaking_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvBreakingNews.apply {
            layoutManager  = LinearLayoutManager(this@BreakingNewsFragment.requireContext(), RecyclerView.VERTICAL, false)
            adapter = newsAdapter
            addOnScrollListener(this@BreakingNewsFragment.scrollLister)
            addOnItemTouchListener(RecyclerViewTouchListener(this@BreakingNewsFragment.requireContext(),this) {
                val item =  newsAdapter.differ.currentList[it]
                val bundle  = Bundle()
                bundle.putParcelable("article",item)
                findNavController().navigate(R.id.action_breakingNewsFragment_to_articleFragment,bundle)

            })

        }
        mainViewModel.getBreakingNews("US")
        mainViewModel.breakNewsLiveData.observe(viewLifecycleOwner){
            when(it){
                is ApiResources.Loading -> showHideProgressBar(true)
                is ApiResources.Success -> {
                    it.data?.let { response ->
                        val totalPages   = response.totalResults/20 + 2
                        isLastPage = mainViewModel.breakingNewsPage == totalPages
                        updateAdapter(response)

                    }
                }
                is ApiResources.Failed -> showErrorMsg()
            }
        }
    }
    var isLoading  = false
    var isScrolling = false
    var isLastPage = false
    val scrollLister = object :RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstElement = layoutManager.findFirstVisibleItemPosition()
            val totalCount = layoutManager.itemCount
            val visibleItemCount = layoutManager.childCount
            val isNotLoadingIsNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstElement+visibleItemCount>=totalCount
            val isTotalMoreThanVisible = totalCount>=20
            val isNotAtBeginiting = firstElement>=0
            val shouldPaginate =  isAtLastItem && isNotAtBeginiting && isTotalMoreThanVisible && isScrolling
            if(shouldPaginate){
                mainViewModel.getBreakingNews("US")
                isScrolling = false
            }


        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }
    }
    fun showErrorMsg(){

        showHideProgressBar(false)
        Snackbar.make(paginationProgressBar, "SomeThingWentWrong", Snackbar.LENGTH_LONG).show()
    }
    fun updateAdapter(response: NewsResponse){
        showHideProgressBar(false)
        newsAdapter.differ.submitList(response.articles.toList())

    }
    fun showHideProgressBar(visibility: Boolean){
        paginationProgressBar.visibility  = if (visibility) View.VISIBLE else View.GONE

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
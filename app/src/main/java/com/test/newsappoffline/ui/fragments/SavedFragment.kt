package com.test.newsappoffline.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.test.newsappoffline.Injection
import com.test.newsappoffline.R
import com.test.newsappoffline.ui.adapters.NewsAdapter
import com.test.newsappoffline.ui.viewmodel.MainViewModel
import com.test.newsappoffline.utils.RecyclerViewTouchListener
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.android.synthetic.main.fragment_saved_news.*

class SavedFragment : Fragment() {
    private val mainViewModel: MainViewModel by lazy {

        ViewModelProvider(requireActivity(), Injection.providesMainFactory(requireContext())).get(MainViewModel::class.java)
    }
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_saved_news, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

    }

    fun initRecyclerView() {
        rvSavedNews.apply {
            layoutManager = LinearLayoutManager(this@SavedFragment.requireContext(), RecyclerView.VERTICAL, false)
            adapter = newsAdapter
        }
        mainViewModel.getSavedNews().observe(this.viewLifecycleOwner) {
            newsAdapter.differ.submitList(it)
        }

        val touchHelper =
            object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val article = newsAdapter.differ.currentList[position]
                    mainViewModel.delteArticle(article)
                    Snackbar.make(rvSavedNews, "Delete Article SuccessFully", Snackbar.LENGTH_LONG).setAction("Undo") {
                        mainViewModel.saveArticle(article)
                    }.show()

                }


            }
        ItemTouchHelper(touchHelper).apply {
            attachToRecyclerView(rvSavedNews)
        }

    }
}

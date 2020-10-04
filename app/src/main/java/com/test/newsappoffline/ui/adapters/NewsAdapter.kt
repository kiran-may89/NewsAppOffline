package com.test.newsappoffline.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.newsappoffline.R
import com.test.newsappoffline.models.news.Article
import kotlinx.android.synthetic.main.item_article_preview.view.*

class NewsAdapter() : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url;
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffUtil)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_article_preview,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)

    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class NewsViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bind(article: Article){
            view.apply {
                Glide.with(this).load(article.urlToImage).into(ivArticleImage)
                tvSource.text = article.source.name
                tvTitle.text = article.title
                tvDescription.text = article.description
                tvPublishedAt.text = article.publishedAt
            }
        }
    }
}
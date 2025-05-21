package com.example.newsaggregator.feature_news_main.presentation.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsaggregator.R
import com.example.newsaggregator.core.util.toDateUi
import com.example.newsaggregator.databinding.ArticleListItemBinding
import com.example.newsaggregator.feature_news_main.data.repository.models.Article

class ArticleListAdapter(private val onClickItem: (url: String) -> Unit = {}) :
    RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder>() {

    var articleList = emptyList<Article>()
        set(value) {
            val callback = CommonCallbackImpl(
                field,
                value,
                areItemsTheSame = { oldItem, newItem ->
                    oldItem.link == newItem.link
                }
            )
            field = value
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
        }


    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val articleCoverIV =
            itemView.findViewById<ImageView>(R.id.article_cover)
        private val articleTitleTV =
            itemView.findViewById<TextView>(R.id.article_title)
        private val articleCategoriesTV =
            itemView.findViewById<TextView>(R.id.categories)
        private val creatorTV =
            itemView.findViewById<TextView>(R.id.creator)
        private val publishedDateTV =
            itemView.findViewById<TextView>(R.id.published_date)


        fun onBind(article: Article) {
            Glide.with(itemView.context)
                .load(article.imageUrl)
                .error(R.drawable.ic_newspaper)
                .into(articleCoverIV)
            articleTitleTV.text = article.title
            articleCategoriesTV.text = '#' + article.categories.joinToString("# ")
            creatorTV.text = article.creator
            publishedDateTV.text = article.publishedDate.toDateUi()

            itemView.setOnClickListener {
                onClickItem(article.link)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding =
            ArticleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding.root)
    }

    override fun getItemCount(): Int = articleList.size


    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.onBind(articleList[position])
    }
}


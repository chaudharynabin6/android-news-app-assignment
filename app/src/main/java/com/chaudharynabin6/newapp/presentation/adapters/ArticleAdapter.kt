package com.chaudharynabin6.newapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.chaudharynabin6.newapp.databinding.RvArticleItemLayoutBinding
import com.chaudharynabin6.newapp.domain.entity.ArticleEntity
import javax.inject.Inject


class ArticleAdapter @Inject constructor(
    private val glide: RequestManager,
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(val binding: RvArticleItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding =
            RvArticleItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun getItemId(position: Int): Long {
        return articleList[position].id.toLong()

    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articleList[position]
        holder.binding.apply {

//            image setup
            glide.load(article.urlToImage).into(rvImage)

//            button setup
            rvSaveButton.setOnClickListener {

                onSavedButtonClickListener?.let { click ->
                    click(article)
                }
//                it.isSelected = true : error because recycle view is recycled

            }

            root.setOnClickListener {
                onItemClickListener?.let {
                    click ->
                    click(article)
                }
            }
            rvSaveButton.isSelected = article.isSaved

//            author
            rvAuthor.text = article.author

//          description
            rvDescription.text = article.description
//            title
            rvTitle.text = article.title

        }


    }

    override fun getItemCount(): Int {
        return articleList.size
    }


    private val diffCallBack = object : DiffUtil.ItemCallback<ArticleEntity>() {
        override fun areItemsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    var articleList: List<ArticleEntity>
        get() = differ.currentList
        set(value) = differ.submitList(value)


    private var onSavedButtonClickListener: ((ArticleEntity) -> Unit)? = null

    fun setOnSavedButtonClickListener(listener: (ArticleEntity) -> Unit) {
        onSavedButtonClickListener = listener
    }


    private var onItemClickListener: ((ArticleEntity) -> Unit)? = null

    fun setOnItemClickListener(listener: (ArticleEntity) -> Unit) {
        onItemClickListener = listener
    }


}
package com.chaudharynabin6.newapp.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.chaudharynabin6.newapp.R
import com.chaudharynabin6.newapp.data.datasources.local.NewsDataBase
import com.chaudharynabin6.newapp.data.datasources.local.TitleEntityLocal
import com.chaudharynabin6.newapp.data.datasources.remote.NewsAPI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class NewsHeadlinesFragment : Fragment(R.layout.fragment_news_headlines) {
    @Inject
    lateinit var newsAPI: NewsAPI

    @Inject
    lateinit var newsDataBase: NewsDataBase
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
//            val data = newsAPI.getNews(q = "tesla")
//            Log.e("news api", data.totalResults.toString())
//            val articles = data.articles.mapNotNull {
//                it.toArticleEntity()
//            }
//            Timber.e("articles : $articles")
            val titleLocalEntity = TitleEntityLocal("test news tittle")
            newsDataBase.dao().insertTitle(titleLocalEntity)

            newsDataBase.dao().getAllTitleSortedByDateSavedDesc().observe(viewLifecycleOwner){
                it?.let {
                    Timber.e("title : $it")
                }
            }
        }
    }
}
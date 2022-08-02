package com.chaudharynabin6.newapp.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.chaudharynabin6.newapp.R
import com.chaudharynabin6.newapp.data.datasources.local.NewsDataBase
import com.chaudharynabin6.newapp.data.datasources.local.TitleEntityLocal
import com.chaudharynabin6.newapp.data.datasources.remote.NewsAPI
import com.chaudharynabin6.newapp.data.mapper.toTitleEntity
import com.chaudharynabin6.newapp.domain.repository.NewsRepository
import com.chaudharynabin6.newapp.other.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class NewsHeadlinesFragment : Fragment(R.layout.fragment_news_headlines) {
    @Inject
    lateinit var newsAPI: NewsAPI

    @Inject
    lateinit var newsDataBase: NewsDataBase

    @Inject
    lateinit var newsRepository: NewsRepository
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
//            newsDataBase.dao().insertTitle(titleLocalEntity)

//            newsDataBase.dao().getAllTitleSortedByDateSavedDesc().collect(){
//
//                    Timber.e("title : $it")
//
//            }

//            newsRepository.getArticles(query = "tesla").collect(){
//                Timber.e("$it")
//                when(it){
//                    is Resource.Success -> {
//                        Timber.e("${it.data}")
//                    }
//                }
//            }

//            newsRepository.insertTitle(titleLocalEntity.toTitleEntity())
//            newsRepository.getAllSavedTitles().collect(){
//                Timber.e("$it")
//            }

        }
    }
}
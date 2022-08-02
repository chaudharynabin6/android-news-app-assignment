package com.chaudharynabin6.newapp.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.chaudharynabin6.newapp.R
import com.chaudharynabin6.newapp.data.datasources.remote.NewsAPI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NewsHeadlinesFragment : Fragment(R.layout.fragment_news_headlines) {
    @Inject
    lateinit var newsAPI : NewsAPI

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            val data =  newsAPI.getNews(q = "tesla")
            Log.e("news api",data.totalResults.toString())
        }
    }
}
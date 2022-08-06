package com.chaudharynabin6.newapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chaudharynabin6.newapp.R
import com.chaudharynabin6.newapp.databinding.FragmentNewsHeadlinesBinding
import com.chaudharynabin6.newapp.other.utils.collectLatestLiveCycleFlow
import com.chaudharynabin6.newapp.other.utils.collectLiveCycleFlow
import com.chaudharynabin6.newapp.presentation.adapters.ArticleAdapter
import com.chaudharynabin6.newapp.presentation.viewmodels.NewsHeadlineViewModel
import com.chaudharynabin6.newapp.presentation.viewmodels.NewsHeadlineViewModelEvents
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsHeadlinesFragment : Fragment(R.layout.fragment_news_headlines) {

    @Inject
    lateinit var adapter: ArticleAdapter

    private lateinit var binding: FragmentNewsHeadlinesBinding

    private val viewModel: NewsHeadlineViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeStates()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentNewsHeadlinesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    private fun setupRecyclerView() {
        binding.nhRvArticle.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
        adapter.setOnItemClickListener {
            viewModel.sendEvent(
                events = NewsHeadlineViewModelEvents.SaveNews(articleEntity = it)
            )
            Snackbar.make(requireView(), "News Saved", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun observeStates() {
       collectLatestLiveCycleFlow(viewModel.articleList){
           adapter.articleList = it
       }
    }

}
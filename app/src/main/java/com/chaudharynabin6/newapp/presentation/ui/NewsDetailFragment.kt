package com.chaudharynabin6.newapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.chaudharynabin6.newapp.R
import com.chaudharynabin6.newapp.databinding.FragmentNewsDetailBinding
import com.chaudharynabin6.newapp.other.utils.collectLatestLiveCycleFlow
import com.chaudharynabin6.newapp.presentation.viewmodels.NewsHeadlineViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsDetailFragment : Fragment(R.layout.fragment_news_detail) {

    private val args: NewsDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentNewsDetailBinding

    @Inject
    lateinit var glide: RequestManager

    private val viewModel: NewsHeadlineViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNewsDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        collectLatestLiveCycleFlow(viewModel.articleList) { list ->
            val articleEntity = list.find { it.id == args.articleId }

            binding.apply {
                articleEntity?.let { article ->
                    glide.load(article.urlToMage).into(ndImage)

                    ndAuthor.text = article.author

                    ndDate.text = article.publishedAt.toString()

                    ndTitle.text = article.title

                    ndDescription.text = article.description

                    ndContent.text = article.content

                }
            }

        }
    }
}
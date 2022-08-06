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
import com.chaudharynabin6.newapp.presentation.viewmodels.NewsHeadlineViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsDetailFragment : Fragment(R.layout.fragment_news_detail) {
//    info: nav safe args with parcelable
//    https://www.raywenderlich.com/19327407-using-safe-args-with-the-android-navigation-component
//    https://developer.android.com/guide/navigation/navigation-pass-data
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        changing the title of action bar
        requireActivity().title = "News Detail"
    }


    override fun onResume() {
        super.onResume()
        requireActivity().title = "News Detail"
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        binding.apply {

            glide.load(args.urlToImage).into(ndImage)

            ndAuthor.text = args.author

            ndDate.text = args.publishedAt

            ndTitle.text = args.title

            ndDescription.text = args.description

            ndContent.text = args.content

        }

    }
}

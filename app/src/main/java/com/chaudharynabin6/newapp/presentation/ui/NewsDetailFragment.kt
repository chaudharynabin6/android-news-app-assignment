package com.chaudharynabin6.newapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
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

            Glide.with(requireContext()).addDefaultRequestListener(
                object : RequestListener<Any> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Any>?,
                        isFirstResource: Boolean,
                    ): Boolean {

                        ndProgressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Any?,
                        model: Any?,
                        target: Target<Any>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean,
                    ): Boolean {
                        ndProgressBar.visibility = View.GONE
                        return false
                    }

                }
            ).setDefaultRequestOptions(
                RequestOptions().error(
                    R.drawable.image_placeholder
                )
            ).load(args.urlToImage).into(ndImage)

            ndAuthor.text = args.author

            ndDate.text = args.publishedAt

            ndTitle.text = args.title

            ndDescription.text = args.description

            ndContent.text = args.content

        }

    }
}

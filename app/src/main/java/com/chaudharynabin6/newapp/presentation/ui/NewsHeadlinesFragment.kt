package com.chaudharynabin6.newapp.presentation.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chaudharynabin6.newapp.R
import com.chaudharynabin6.newapp.databinding.FragmentNewsHeadlinesBinding
import com.chaudharynabin6.newapp.other.AppConstants
import com.chaudharynabin6.newapp.other.utils.collectLatestLiveCycleFlow
import com.chaudharynabin6.newapp.presentation.adapters.ArticleAdapter
import com.chaudharynabin6.newapp.presentation.viewmodels.NewsHeadlineViewModel
import com.chaudharynabin6.newapp.presentation.viewmodels.NewsHeadlineViewModelEvents
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class NewsHeadlinesFragment : Fragment(R.layout.fragment_news_headlines) {

    @Inject
    lateinit var adapter: ArticleAdapter

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var binding: FragmentNewsHeadlinesBinding

    private val viewModel: NewsHeadlineViewModel by viewModels()

    private var title: String by Delegates.observable("News") { _, _, newValue ->
        sharedPreferences.edit()
            .putString(AppConstants.TITLE_KEY, newValue)
            .apply()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "${sharedPreferences.getString(AppConstants.TITLE_KEY, "")}"
        title = if(title == "") "News" else title
        requireActivity().title = title
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeStates()
        createSearchView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentNewsHeadlinesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        requireActivity().title = title
    }

    private fun setupRecyclerView() {
        binding.nhRvArticle.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())

//            https://stackoverflow.com/questions/29331075/recyclerview-blinking-after-notifydatasetchanged
//            fixing the flicking of recycler view
            it.itemAnimator?.changeDuration = 0
        }
        adapter.setOnSavedButtonClickListener {
            viewModel.sendEvent(
                event = NewsHeadlineViewModelEvents.SaveNews(articleEntity = it)
            )
            Snackbar.make(requireView(), "News Saved", Snackbar.LENGTH_LONG).show()
        }

        adapter.setOnItemClickListener {
            val action =
                NewsHeadlinesFragmentDirections.actionNewsHeadlinesFragmentToNewsDetailFragment(
                    id = it.id,
                    author = it.author,
                    description = it.description,
                    publishedAt = it.publishedAt.toString(),
                    urlToImage = it.urlToImage,
                    content = it.content,
                    title = it.title
                )
            findNavController().navigate(action)
        }
    }

    private fun observeStates() {
        collectLatestLiveCycleFlow(viewModel.articleList) {
            adapter.articleList = it
        }
    }

    //   https://pluu.github.io/blog/android/2021/09/06/menuhost/
    // https://www.tutorialsbuzz.com/2020/09/Android-SearchView-ActionBar-toolbar-Kotlin.html
    private fun createSearchView() {


        val menuProvider = object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.action_menu_fragment_news_headlines, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.action_search -> {

                        val searchView = menuItem.actionView as SearchView

                        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                            override fun onQueryTextSubmit(query: String): Boolean {
//                        https://stackoverflow.com/questions/17506230/how-do-i-close-a-searchview-programmatically
                                menuItem.collapseActionView()
                                title = "$query news"
                                requireActivity().title = "$query news"
                                viewModel.sendEvent(
                                    event = NewsHeadlineViewModelEvents.SearchNews(
                                        query = query
                                    )
                                )
                                return true
                            }

                            override fun onQueryTextChange(query: String): Boolean {
                                Log.e("onQueryTextChange", "query: " + query)
                                if (query.isNotEmpty() && query.isNotBlank()) {
                                    title = "$query news"
                                    requireActivity().title = "$query news"
                                    viewModel.sendEvent(
                                        event = NewsHeadlineViewModelEvents.SearchNews(
                                            query = query
                                        )
                                    )
                                }


                                return true
                            }
                        })


                        //Expand Collapse listener
                        menuItem.setOnActionExpandListener(object :
                            MenuItem.OnActionExpandListener {
                            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                                showToast("Action Collapse")
                                return true
                            }

                            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                                showToast("Action Expand")
                                return true
                            }

                            fun showToast(msg: String) {
                                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                }
                return true
            }
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}



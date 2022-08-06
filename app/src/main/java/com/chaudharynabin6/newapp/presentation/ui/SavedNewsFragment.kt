package com.chaudharynabin6.newapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chaudharynabin6.newapp.R
import com.chaudharynabin6.newapp.databinding.FragmentSavedNewsBinding
import com.chaudharynabin6.newapp.other.utils.collectLatestLiveCycleFlow
import com.chaudharynabin6.newapp.presentation.adapters.TitleAdapter
import com.chaudharynabin6.newapp.presentation.viewmodels.SavedNewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {

    @Inject
    lateinit var titleAdapter: TitleAdapter

    private lateinit var binding: FragmentSavedNewsBinding

    private val viewModel: SavedNewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        changing the title of action bar
        requireActivity().title = "Saved Title"
    }


    override fun onResume() {
        super.onResume()
        requireActivity().title = "Saved Title"
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSavedNewsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeFlows()

    }

    private fun setupRecyclerView() {
        binding.rvSnRecyclerView.apply {
            adapter = titleAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator?.changeDuration = 0
        }

    }

    private fun observeFlows() {
        collectLatestLiveCycleFlow(viewModel.savedTitles) {
            titleAdapter.titleList = it
        }

    }
}
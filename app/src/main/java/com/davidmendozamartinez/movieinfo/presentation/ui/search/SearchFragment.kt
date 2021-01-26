package com.davidmendozamartinez.movieinfo.presentation.ui.search

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.transition.Slide
import androidx.transition.Transition
import com.davidmendozamartinez.movieinfo.R
import com.davidmendozamartinez.movieinfo.databinding.FragmentSearchBinding
import com.davidmendozamartinez.movieinfo.presentation.ui.adapter.MovieAdapter
import com.davidmendozamartinez.movieinfo.presentation.util.*
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding get() = _binding!!

    private val viewModel: SearchViewModel by viewModel()
    private lateinit var adapter: MovieAdapter

    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        enterTransition = MaterialContainerTransform().apply {
            startView = requireActivity().findViewById(R.id.fab)
            endView = binding.container
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
            scrimColor = Color.TRANSPARENT
            containerColor = requireContext().themeColor(R.attr.colorSurface)
            startContainerColor = requireContext().themeColor(R.attr.colorSecondary)
            endContainerColor = requireContext().themeColor(R.attr.colorSurface)
            addListener(object : OnTransitionEndListener {
                override fun onTransitionEnd(transition: Transition) {
                    binding.editTextSearch.showIme()
                }
            })
        }

        returnTransition = Slide().apply {
            duration = resources.getInteger(R.integer.motion_duration_medium).toLong()
            addTarget(R.id.container)
        }

        setupList()
        setupSearcher()

        viewModel.currentQuery.observe(viewLifecycleOwner) {
            binding.editTextSearch.hideIme()
            if (it.isNotEmpty()) searchMovies(it)
        }
    }

    private fun setupList() {
        adapter = MovieAdapter { _, id -> navigateToDetails(id) }
        adapter.addLoadStateListener { loadState ->
            val state: DataState = when (loadState.source.refresh) {
                is LoadState.Loading -> DataState.LOADING
                is LoadState.Error -> DataState.ERROR
                is LoadState.NotLoading ->
                    if (loadState.append.endOfPaginationReached && adapter.itemCount == 0)
                        DataState.EMPTY
                    else DataState.SUCCESS
            }
            viewModel.setState(state)
        }

        binding.listContainer.recyclerView.adapter = adapter
        binding.errorState.buttonRetry.setOnClickListener { adapter.retry() }
    }

    private fun setupSearcher() {
        binding.container.setStatusBarPadding()
        binding.toolbar.setNavigationOnClickListener {
            navigateUp()
            binding.editTextSearch.hideIme()
        }

        with(binding.editTextSearch) {
            setOnEditorActionListener { view, actionId, _ ->
                (EditorInfo.IME_ACTION_SEARCH == actionId).also { isActionSearch ->
                    if (isActionSearch) {
                        searchMovies(view.text.toString())
                        view.hideIme()
                    }
                }
            }
        }
    }

    private fun searchMovies(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchMovies(query.trim()).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun navigateToDetails(movieId: Int) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailsFragment(movieId)
        )
    }

    private fun navigateUp() {
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.davidmendozamartinez.movieinfo.presentation.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.davidmendozamartinez.movieinfo.databinding.FragmentSearchBinding
import com.davidmendozamartinez.movieinfo.presentation.ui.adapter.MovieAdapter
import com.davidmendozamartinez.movieinfo.presentation.util.hideIme
import com.davidmendozamartinez.movieinfo.presentation.util.setStatusBarPadding
import com.davidmendozamartinez.movieinfo.presentation.util.showIme
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
        setupList()
        setupSearcher()

        viewModel.currentQuery.observe(viewLifecycleOwner) {
            with(binding.editTextSearch) { if (it.isEmpty()) showIme() else hideIme() }
            searchMovies(it)
        }
    }

    private fun setupList() {
        adapter = MovieAdapter { navigateToDetails(it) }
        binding.listContainer.recyclerView.adapter = adapter
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
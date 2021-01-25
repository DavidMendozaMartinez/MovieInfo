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
import com.davidmendozamartinez.movieinfo.presentation.util.setStatusBarPadding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding get() = _binding!!
    private val viewModel: SearchViewModel by viewModel()

    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupList()
        binding.searchContainer.setStatusBarPadding()
        binding.searchToolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                binding.searchEditText.text.trim().let {
                    if (it.isNotEmpty())
                        lifecycleScope.launch {
                            viewModel.searchMovies(it.toString()).collectLatest { data ->
                                adapter.submitData(data)
                            }
                        }
                }
                true
            } else {
                false
            }
        }
    }

    private fun setupList() {
        adapter = MovieAdapter {
            val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(it)
            findNavController().navigate(action)
        }
        binding.listContainer.recyclerView.adapter = adapter
    }
}
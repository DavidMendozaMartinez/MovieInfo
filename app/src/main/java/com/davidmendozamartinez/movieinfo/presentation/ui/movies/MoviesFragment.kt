package com.davidmendozamartinez.movieinfo.presentation.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.davidmendozamartinez.movieinfo.databinding.FragmentMoviesBinding
import com.davidmendozamartinez.movieinfo.presentation.ui.adapter.MovieAdapter
import com.davidmendozamartinez.movieinfo.presentation.util.setStatusBarPadding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment() {
    private var _binding: FragmentMoviesBinding? = null
    private val binding: FragmentMoviesBinding get() = _binding!!

    private val viewModel: MoviesViewModel by viewModel()
    private val args: MoviesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupList()
    }

    private fun setupList() {
        val adapter = MovieAdapter { navigateToDetails(it) }
        binding.listContainer.recyclerView.adapter = adapter
        binding.listContainer.recyclerView.setStatusBarPadding()

        lifecycleScope.launch {
            viewModel.getMovies(args.section).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun navigateToDetails(movieId: Int) {
        findNavController().navigate(
            MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(movieId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
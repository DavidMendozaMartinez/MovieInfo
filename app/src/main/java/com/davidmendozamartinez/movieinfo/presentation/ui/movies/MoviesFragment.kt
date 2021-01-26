package com.davidmendozamartinez.movieinfo.presentation.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.davidmendozamartinez.movieinfo.R
import com.davidmendozamartinez.movieinfo.databinding.FragmentMoviesBinding
import com.davidmendozamartinez.movieinfo.presentation.ui.adapter.MovieAdapter
import com.davidmendozamartinez.movieinfo.presentation.util.DataState
import com.davidmendozamartinez.movieinfo.presentation.util.setStatusBarPadding
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment() {
    private var _binding: FragmentMoviesBinding? = null
    private val binding: FragmentMoviesBinding get() = _binding!!

    private val viewModel: MoviesViewModel by viewModel()
    private val args: MoviesFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough().apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        setupList()
    }

    private fun setupList() {
        val adapter = MovieAdapter { view, id -> navigateToDetails(view, id) }
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
        binding.listContainer.recyclerView.setStatusBarPadding()
        binding.errorState.buttonRetry.setOnClickListener { adapter.retry() }

        lifecycleScope.launch {
            viewModel.getMovies(args.section).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun navigateToDetails(view: View, movieId: Int) {
        exitTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }

        val detailsTransitionName = getString(R.string.details_transition_name)
        val extras = FragmentNavigatorExtras(view to detailsTransitionName)
        findNavController().navigate(
            MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(movieId),
            extras
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
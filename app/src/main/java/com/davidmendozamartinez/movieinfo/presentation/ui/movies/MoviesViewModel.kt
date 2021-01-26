package com.davidmendozamartinez.movieinfo.presentation.ui.movies

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.davidmendozamartinez.movieinfo.domain.model.MovieDomain
import com.davidmendozamartinez.movieinfo.domain.usecase.GetFavoriteMoviesUseCase
import com.davidmendozamartinez.movieinfo.domain.usecase.GetPopularMoviesUseCase
import com.davidmendozamartinez.movieinfo.domain.usecase.GetTopRatedMoviesUseCase
import com.davidmendozamartinez.movieinfo.domain.usecase.GetUpcomingMoviesUseCase
import com.davidmendozamartinez.movieinfo.presentation.model.MovieUI
import com.davidmendozamartinez.movieinfo.presentation.model.Section
import com.davidmendozamartinez.movieinfo.presentation.model.toPresentation
import com.davidmendozamartinez.movieinfo.presentation.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase
) : ViewModel() {

    private var currentMovies: Flow<PagingData<MovieUI>>? = null
    private var _currentState: MutableLiveData<DataState> = MutableLiveData(DataState.LOADING)

    val loadingStateVisibility: LiveData<Boolean> =
        Transformations.map(_currentState) { it == DataState.LOADING }

    val errorStateVisibility: LiveData<Boolean> =
        Transformations.map(_currentState) { it == DataState.ERROR }

    val emptyStateVisibility: LiveData<Boolean> =
        Transformations.map(_currentState) { it == DataState.EMPTY }

    val successStateVisibility: LiveData<Boolean> =
        Transformations.map(_currentState) { it == DataState.SUCCESS }

    fun getMovies(section: Section): Flow<PagingData<MovieUI>> {
        return currentMovies ?: requestMovies(section)
            .map { pagingData -> pagingData.map { it.toPresentation() } }
            .cachedIn(viewModelScope)
            .also { currentMovies = it }
    }

    private fun requestMovies(section: Section): Flow<PagingData<MovieDomain>> = when (section) {
        Section.POPULAR -> getPopularMoviesUseCase.invoke()
        Section.TOP_RATED -> getTopRatedMoviesUseCase.invoke()
        Section.UPCOMING -> getUpcomingMoviesUseCase.invoke()
        Section.FAVORITES -> getFavoriteMoviesUseCase.invoke()
    }

    fun setState(state: DataState) {
        _currentState.value = state
    }
}
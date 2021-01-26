package com.davidmendozamartinez.movieinfo.presentation.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase
) : ViewModel() {

    private var currentMovies: Flow<PagingData<MovieUI>>? = null

    private var _successStateVisibility: MutableLiveData<Boolean> = MutableLiveData(false)
    val successStateVisibility: LiveData<Boolean> get() = _successStateVisibility

    private var _loadingStateVisibility: MutableLiveData<Boolean> = MutableLiveData(false)
    val loadingStateVisibility: LiveData<Boolean> get() = _loadingStateVisibility

    private var _emptyStateVisibility: MutableLiveData<Boolean> = MutableLiveData(false)
    val emptyStateVisibility: LiveData<Boolean> get() = _emptyStateVisibility

    private var _errorStateVisibility: MutableLiveData<Boolean> = MutableLiveData(false)
    val errorStateVisibility: LiveData<Boolean> get() = _errorStateVisibility

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

    fun setSuccessState(visible: Boolean) {
        _successStateVisibility.value = visible
    }

    fun setLoadingState(visible: Boolean) {
        _loadingStateVisibility.value = visible
    }

    fun setEmptyState(visible: Boolean) {
        _emptyStateVisibility.value = visible
    }

    fun setErrorState(visible: Boolean) {
        _errorStateVisibility.value = visible
    }
}
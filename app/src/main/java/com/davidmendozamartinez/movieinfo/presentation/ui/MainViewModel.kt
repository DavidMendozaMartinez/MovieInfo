package com.davidmendozamartinez.movieinfo.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.davidmendozamartinez.movieinfo.domain.usecase.GetPopularMoviesUseCase
import com.davidmendozamartinez.movieinfo.presentation.model.MovieUI
import com.davidmendozamartinez.movieinfo.presentation.model.toPresentation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MainViewModel(private val useCase: GetPopularMoviesUseCase) : ViewModel() {

    private var movies: Flow<PagingData<MovieUI>>? = null

    init {
        getPopularMovies()
    }

    fun getPopularMovies(): Flow<PagingData<MovieUI>> {
        return movies ?: useCase.invoke()
            .map { pagingData ->
                pagingData.map { it.toPresentation() }
            }
            .cachedIn(viewModelScope)
            .also { movies = it }
    }
}
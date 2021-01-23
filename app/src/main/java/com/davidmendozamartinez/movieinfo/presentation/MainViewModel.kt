package com.davidmendozamartinez.movieinfo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.davidmendozamartinez.movieinfo.domain.usecase.GetPopularMoviesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MainViewModel(private val useCase: GetPopularMoviesUseCase) : ViewModel() {

    private var currentMovies: Flow<PagingData<MovieUI>>? = null

    init {
        getPopularMovies()
    }

    fun getPopularMovies(): Flow<PagingData<MovieUI>> {
        return currentMovies ?: useCase.invoke()
            .map { pagingData ->
                pagingData.map { it.toPresentation() }
            }
            .cachedIn(viewModelScope)
            .also { currentMovies = it }
    }
}
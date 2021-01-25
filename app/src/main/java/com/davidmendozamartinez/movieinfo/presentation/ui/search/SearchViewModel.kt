package com.davidmendozamartinez.movieinfo.presentation.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.davidmendozamartinez.movieinfo.domain.usecase.SearchMoviesUseCase
import com.davidmendozamartinez.movieinfo.presentation.model.MovieUI
import com.davidmendozamartinez.movieinfo.presentation.model.toPresentation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchViewModel(
    private val useCase: SearchMoviesUseCase
) : ViewModel() {

    private var _currentQuery: MutableLiveData<String> = MutableLiveData<String>("")
    val currentQuery: LiveData<String> get() = _currentQuery

    private var currentMovies: Flow<PagingData<MovieUI>>? = null

    fun searchMovies(query: String): Flow<PagingData<MovieUI>> {
        val lastResult = currentMovies

        if ((query.trim().isEmpty() || query == _currentQuery.value) && lastResult != null) {
            return lastResult
        }

        _currentQuery.value = query

        return useCase.invoke(query)
            .map { pagingData -> pagingData.map { it.toPresentation() } }
            .cachedIn(viewModelScope)
            .also { currentMovies = it }
    }
}
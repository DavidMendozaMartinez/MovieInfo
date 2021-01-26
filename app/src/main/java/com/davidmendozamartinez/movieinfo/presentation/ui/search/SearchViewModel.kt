package com.davidmendozamartinez.movieinfo.presentation.ui.search

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.davidmendozamartinez.movieinfo.domain.usecase.SearchMoviesUseCase
import com.davidmendozamartinez.movieinfo.presentation.model.MovieUI
import com.davidmendozamartinez.movieinfo.presentation.model.toPresentation
import com.davidmendozamartinez.movieinfo.presentation.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchViewModel(
    private val useCase: SearchMoviesUseCase
) : ViewModel() {

    private var _currentQuery: MutableLiveData<String> = MutableLiveData<String>("")
    val currentQuery: LiveData<String> get() = _currentQuery

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

    fun searchMovies(query: String): Flow<PagingData<MovieUI>> {
        val lastResult = currentMovies
        if (query == _currentQuery.value && lastResult != null) {
            return lastResult
        }

        _currentQuery.value = query

        return useCase.invoke(query)
            .map { pagingData -> pagingData.map { it.toPresentation() } }
            .cachedIn(viewModelScope)
            .also { currentMovies = it }
    }

    fun setState(state: DataState) {
        _currentState.value = state
    }
}
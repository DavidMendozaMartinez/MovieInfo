package com.davidmendozamartinez.movieinfo.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidmendozamartinez.movieinfo.domain.usecase.GetPopularMoviesUseCase
import kotlinx.coroutines.launch

class MainViewModel(private val useCase: GetPopularMoviesUseCase) : ViewModel() {

    private val _movies: MutableLiveData<List<MovieUI>> = MutableLiveData()
    val movies: LiveData<List<MovieUI>> get() = _movies

    init {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            _movies.value = useCase.invoke().map { it.toPresentation() }
        }
    }
}
package com.davidmendozamartinez.movieinfo.presentation.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidmendozamartinez.movieinfo.domain.usecase.GetMovieDetailsUseCase
import com.davidmendozamartinez.movieinfo.presentation.model.MovieDetailsUI
import com.davidmendozamartinez.movieinfo.presentation.model.toPresentation
import kotlinx.coroutines.launch

class DetailsViewModel(private val useCase: GetMovieDetailsUseCase) : ViewModel() {

    private var _movieDetails: MutableLiveData<MovieDetailsUI> = MutableLiveData()
    val movieDetails: LiveData<MovieDetailsUI> get() = _movieDetails

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _movieDetails.value = useCase.invoke(movieId).toPresentation()
        }
    }
}
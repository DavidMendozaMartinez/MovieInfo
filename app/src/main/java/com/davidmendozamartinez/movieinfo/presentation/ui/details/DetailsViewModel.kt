package com.davidmendozamartinez.movieinfo.presentation.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidmendozamartinez.movieinfo.domain.usecase.AddFavoriteMovieUseCase
import com.davidmendozamartinez.movieinfo.domain.usecase.GetMovieDetailsUseCase
import com.davidmendozamartinez.movieinfo.domain.usecase.IsFavoriteMovieUseCase
import com.davidmendozamartinez.movieinfo.domain.usecase.RemoveFavoriteMovieUseCase
import com.davidmendozamartinez.movieinfo.presentation.model.MovieDetailsUI
import com.davidmendozamartinez.movieinfo.presentation.model.MovieUI
import com.davidmendozamartinez.movieinfo.presentation.model.toDomain
import com.davidmendozamartinez.movieinfo.presentation.model.toPresentation
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val isFavoriteMovieUseCase: IsFavoriteMovieUseCase,
    private val addFavoriteMovieUseCase: AddFavoriteMovieUseCase,
    private val removeFavoriteMovieUseCase: RemoveFavoriteMovieUseCase
) : ViewModel() {

    private var _movieDetails: MutableLiveData<MovieDetailsUI> = MutableLiveData()
    val movieDetails: LiveData<MovieDetailsUI> get() = _movieDetails

    private var _isFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _isFavorite.value = isFavoriteMovieUseCase.invoke(movieId)
            _movieDetails.value = getMovieDetailsUseCase.invoke(movieId).toPresentation()
        }
    }

    fun addToFavorites(details: MovieDetailsUI) {
        viewModelScope.launch {
            with(details) {
                addFavoriteMovieUseCase.invoke(MovieUI(posterPath, id, title).toDomain())
                _isFavorite.value = true
            }
        }
    }

    fun removeFromFavorites(details: MovieDetailsUI) {
        viewModelScope.launch {
            with(details) {
                removeFavoriteMovieUseCase.invoke(MovieUI(posterPath, id, title).toDomain())
                _isFavorite.value = false
            }
        }
    }
}
package com.davidmendozamartinez.movieinfo.presentation.ui.details

import androidx.lifecycle.*
import com.davidmendozamartinez.movieinfo.domain.usecase.AddFavoriteMovieUseCase
import com.davidmendozamartinez.movieinfo.domain.usecase.GetMovieDetailsUseCase
import com.davidmendozamartinez.movieinfo.domain.usecase.IsFavoriteMovieUseCase
import com.davidmendozamartinez.movieinfo.domain.usecase.RemoveFavoriteMovieUseCase
import com.davidmendozamartinez.movieinfo.presentation.model.MovieDetailsUI
import com.davidmendozamartinez.movieinfo.presentation.model.MovieUI
import com.davidmendozamartinez.movieinfo.presentation.model.toDomain
import com.davidmendozamartinez.movieinfo.presentation.model.toPresentation
import com.davidmendozamartinez.movieinfo.presentation.util.DataState
import com.davidmendozamartinez.movieinfo.presentation.util.DataState.*
import com.davidmendozamartinez.movieinfo.presentation.util.setOnGetDataListener
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
    private val isFavorite: LiveData<Boolean> get() = _isFavorite

    private var _currentState: MutableLiveData<DataState> = MutableLiveData(LOADING)

    val loadingStateVisibility: LiveData<Boolean> =
        Transformations.map(_currentState) { it == LOADING }

    val errorStateVisibility: LiveData<Boolean> =
        Transformations.map(_currentState) { it == ERROR }

    val buttonAddToFavoritesVisibility: LiveData<Boolean> =
        Transformations.map(_currentState) { it == SUCCESS && isFavorite.value == false }

    val buttonRemoveFromFavoritesVisibility: LiveData<Boolean> =
        Transformations.map(_currentState) { it == SUCCESS && isFavorite.value == true }

    fun getMovieDetails(movieId: Int) {
        if (_movieDetails.value == null || _isFavorite.value == null) {
            _movieDetails.setOnGetDataListener(viewModelScope,
                onLoading = { _currentState.value = LOADING },
                onSuccess = {
                    getMovieDetailsUseCase.invoke(movieId).toPresentation().also {
                        _isFavorite.value = isFavoriteMovieUseCase.invoke(movieId)
                        _currentState.value = SUCCESS
                    }
                },
                onError = { _currentState.value = ERROR }
            )
        }
    }

    fun addToFavorites(details: MovieDetailsUI) {
        viewModelScope.launch {
            with(details) {
                addFavoriteMovieUseCase.invoke(MovieUI(posterPath, id, title).toDomain())
                _isFavorite.value = true
                _currentState.value = SUCCESS
            }
        }
    }

    fun removeFromFavorites(details: MovieDetailsUI) {
        viewModelScope.launch {
            with(details) {
                removeFavoriteMovieUseCase.invoke(MovieUI(posterPath, id, title).toDomain())
                _isFavorite.value = false
                _currentState.value = SUCCESS
            }
        }
    }
}
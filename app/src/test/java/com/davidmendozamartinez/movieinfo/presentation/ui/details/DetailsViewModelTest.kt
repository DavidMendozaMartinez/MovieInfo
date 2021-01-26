package com.davidmendozamartinez.movieinfo.presentation.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.davidmendozamartinez.movieinfo.MainCoroutineRule
import com.davidmendozamartinez.movieinfo.data.remote.model.toDomain
import com.davidmendozamartinez.movieinfo.data.remote.sampleDetails
import com.davidmendozamartinez.movieinfo.data.repository.FakeMovieRepository
import com.davidmendozamartinez.movieinfo.domain.usecase.AddFavoriteMovieUseCase
import com.davidmendozamartinez.movieinfo.domain.usecase.GetMovieDetailsUseCase
import com.davidmendozamartinez.movieinfo.domain.usecase.IsFavoriteMovieUseCase
import com.davidmendozamartinez.movieinfo.domain.usecase.RemoveFavoriteMovieUseCase
import com.davidmendozamartinez.movieinfo.getOrAwaitValue
import com.davidmendozamartinez.movieinfo.presentation.model.toPresentation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class DetailsViewModelTest {
    private lateinit var viewModel: DetailsViewModel

    private val movieRepository = FakeMovieRepository()

    private val getMovieDetailsUseCase = GetMovieDetailsUseCase(movieRepository)
    private val isFavoriteMovieUseCase = IsFavoriteMovieUseCase(movieRepository)
    private val addFavoriteMovieUseCase = AddFavoriteMovieUseCase(movieRepository)
    private val removeFavoriteMovieUseCase = RemoveFavoriteMovieUseCase(movieRepository)

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        movieRepository.shouldReturnError = false

        viewModel = DetailsViewModel(
            getMovieDetailsUseCase,
            isFavoriteMovieUseCase,
            addFavoriteMovieUseCase,
            removeFavoriteMovieUseCase
        )
    }

    @Test
    fun `When the movie details are requested it returns the expected object`() {
        viewModel.getMovieDetails(1)

        val details = viewModel.movieDetails.getOrAwaitValue()

        assertThat(details, `is`(sampleDetails.toDomain().toPresentation()))
    }

    @Test
    fun `When an error occurs the error state is visible`() {
        movieRepository.shouldReturnError = true

        try {
            viewModel.getMovieDetails(1)
        } catch (error: AssertionError) {

        } finally {
            val loadingStateVisibility = viewModel.loadingStateVisibility.getOrAwaitValue()
            val errorStateVisibility = viewModel.errorStateVisibility.getOrAwaitValue()

            assertThat(loadingStateVisibility, `is`(false))
            assertThat(errorStateVisibility, `is`(true))
        }
    }
}
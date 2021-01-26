package com.davidmendozamartinez.movieinfo.presentation.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.davidmendozamartinez.movieinfo.MainCoroutineRule
import com.davidmendozamartinez.movieinfo.data.repository.FakeMovieRepository
import com.davidmendozamartinez.movieinfo.domain.usecase.GetFavoriteMoviesUseCase
import com.davidmendozamartinez.movieinfo.domain.usecase.GetPopularMoviesUseCase
import com.davidmendozamartinez.movieinfo.domain.usecase.GetTopRatedMoviesUseCase
import com.davidmendozamartinez.movieinfo.domain.usecase.GetUpcomingMoviesUseCase
import com.davidmendozamartinez.movieinfo.getOrAwaitValue
import com.davidmendozamartinez.movieinfo.presentation.util.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MoviesViewModelTest {
    private lateinit var viewModel: MoviesViewModel

    private val movieRepository = FakeMovieRepository()

    private val getPopularMoviesUseCase = GetPopularMoviesUseCase(movieRepository)
    private val getTopRatedMoviesUseCase = GetTopRatedMoviesUseCase(movieRepository)
    private val getUpcomingMoviesUseCase = GetUpcomingMoviesUseCase(movieRepository)
    private val getFavoriteMoviesUseCase = GetFavoriteMoviesUseCase(movieRepository)

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        viewModel = MoviesViewModel(
            getPopularMoviesUseCase,
            getTopRatedMoviesUseCase,
            getUpcomingMoviesUseCase,
            getFavoriteMoviesUseCase
        )
    }

    @Test
    fun `When the movies are loading the progressBar is visible`() {
        viewModel.setState(DataState.LOADING)

        val loadingStateVisibility = viewModel.loadingStateVisibility.getOrAwaitValue()
        val emptyStateVisibility = viewModel.emptyStateVisibility.getOrAwaitValue()
        val successStateVisibility = viewModel.successStateVisibility.getOrAwaitValue()
        val errorStateVisibility = viewModel.errorStateVisibility.getOrAwaitValue()

        assertThat(loadingStateVisibility, `is`(true))
        assertThat(emptyStateVisibility, `is`(false))
        assertThat(successStateVisibility, `is`(false))
        assertThat(errorStateVisibility, `is`(false))
    }

    @Test
    fun `When empty movie list has been received the empty state is visible`() {
        viewModel.setState(DataState.EMPTY)

        val loadingStateVisibility = viewModel.loadingStateVisibility.getOrAwaitValue()
        val emptyStateVisibility = viewModel.emptyStateVisibility.getOrAwaitValue()
        val successStateVisibility = viewModel.successStateVisibility.getOrAwaitValue()
        val errorStateVisibility = viewModel.errorStateVisibility.getOrAwaitValue()

        assertThat(loadingStateVisibility, `is`(false))
        assertThat(emptyStateVisibility, `is`(true))
        assertThat(successStateVisibility, `is`(false))
        assertThat(errorStateVisibility, `is`(false))
    }

    @Test
    fun `When movies have been received the list is visible`() {
        viewModel.setState(DataState.SUCCESS)

        val loadingStateVisibility = viewModel.loadingStateVisibility.getOrAwaitValue()
        val emptyStateVisibility = viewModel.emptyStateVisibility.getOrAwaitValue()
        val successStateVisibility = viewModel.successStateVisibility.getOrAwaitValue()
        val errorStateVisibility = viewModel.errorStateVisibility.getOrAwaitValue()

        assertThat(loadingStateVisibility, `is`(false))
        assertThat(emptyStateVisibility, `is`(false))
        assertThat(successStateVisibility, `is`(true))
        assertThat(errorStateVisibility, `is`(false))
    }

    @Test
    fun `When an error has occurred the error state is visible`() {
        viewModel.setState(DataState.ERROR)

        val loadingStateVisibility = viewModel.loadingStateVisibility.getOrAwaitValue()
        val emptyStateVisibility = viewModel.emptyStateVisibility.getOrAwaitValue()
        val successStateVisibility = viewModel.successStateVisibility.getOrAwaitValue()
        val errorStateVisibility = viewModel.errorStateVisibility.getOrAwaitValue()

        assertThat(loadingStateVisibility, `is`(false))
        assertThat(emptyStateVisibility, `is`(false))
        assertThat(successStateVisibility, `is`(false))
        assertThat(errorStateVisibility, `is`(true))
    }
}
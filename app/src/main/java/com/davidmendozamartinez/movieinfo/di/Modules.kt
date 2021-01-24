package com.davidmendozamartinez.movieinfo.di

import com.davidmendozamartinez.movieinfo.data.local.MovieInfoDatabase
import com.davidmendozamartinez.movieinfo.data.local.source.MovieLocalDataSource
import com.davidmendozamartinez.movieinfo.data.remote.createTheMovieDBService
import com.davidmendozamartinez.movieinfo.data.remote.source.MovieRemoteDataSource
import com.davidmendozamartinez.movieinfo.data.repository.MovieRepositoryImpl
import com.davidmendozamartinez.movieinfo.domain.repository.MovieRepository
import com.davidmendozamartinez.movieinfo.domain.usecase.*
import com.davidmendozamartinez.movieinfo.presentation.ui.details.DetailsViewModel
import com.davidmendozamartinez.movieinfo.presentation.ui.movies.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { MoviesViewModel(get(), get(), get(), get()) }
    viewModel { DetailsViewModel(get(), get(), get(), get()) }
}

val domainModule = module {
    single { GetPopularMoviesUseCase(get()) }
    single { GetTopRatedMoviesUseCase(get()) }
    single { GetUpcomingMoviesUseCase(get()) }
    single { GetMovieDetailsUseCase(get()) }
    single { GetFavoriteMoviesUseCase(get()) }
    single { IsFavoriteMovieUseCase(get()) }
    single { AddFavoriteMovieUseCase(get()) }
    single { RemoveFavoriteMovieUseCase(get()) }
}

val dataModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
    single { MovieRemoteDataSource(get()) }
    single { MovieLocalDataSource(get()) }
    single { createTheMovieDBService() }
    single { MovieInfoDatabase.getInstance(get()) }
}
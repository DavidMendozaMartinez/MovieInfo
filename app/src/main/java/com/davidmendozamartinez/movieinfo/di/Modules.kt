package com.davidmendozamartinez.movieinfo.di

import com.davidmendozamartinez.movieinfo.data.remote.createTheMovieDBService
import com.davidmendozamartinez.movieinfo.data.remote.source.MovieRemoteDataSource
import com.davidmendozamartinez.movieinfo.data.repository.MovieRepositoryImpl
import com.davidmendozamartinez.movieinfo.domain.repository.MovieRepository
import com.davidmendozamartinez.movieinfo.domain.usecase.GetPopularMoviesUseCase
import com.davidmendozamartinez.movieinfo.presentation.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { MainViewModel(get()) }
}

val domainModule = module {
    single { GetPopularMoviesUseCase(get()) }
}

val dataModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get()) }
    single { MovieRemoteDataSource(get()) }
    single { createTheMovieDBService() }
}
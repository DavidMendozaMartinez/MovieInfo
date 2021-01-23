package com.davidmendozamartinez.movieinfo

import android.app.Application
import com.davidmendozamartinez.movieinfo.di.dataModule
import com.davidmendozamartinez.movieinfo.di.domainModule
import com.davidmendozamartinez.movieinfo.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MovieInfoApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MovieInfoApp)
            modules(presentationModule, domainModule, dataModule)
        }
    }
}
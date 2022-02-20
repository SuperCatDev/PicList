package com.supercat.piclist.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

object KoinInitializer {

    private val modules = listOf(
        InteractorModule.module,
        ViewModelModule.module,
        RepositoryModule.module,
        NavigationModule.module,
    )

    fun assembleGraph(application: Application) = startKoin {
        androidContext(application)
        modules(modules)
    }
}
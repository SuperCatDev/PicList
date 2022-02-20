package com.supercat.piclist.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import org.koin.dsl.module

object NavigationModule {
    val module = module {
        single { Cicerone.create() }
        single { get<Cicerone<Router>>().router }
        single { get<Cicerone<Router>>().getNavigatorHolder() }
    }
}
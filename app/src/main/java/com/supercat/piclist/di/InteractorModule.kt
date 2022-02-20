package com.supercat.piclist.di

import com.supercat.piclist.domain.PicturesListInteractor
import com.supercat.piclist.domain.PicturesListIntractorImpl
import org.koin.dsl.module

object InteractorModule {
    val module = module {
        single<PicturesListInteractor> { PicturesListIntractorImpl(get()) }
    }
}
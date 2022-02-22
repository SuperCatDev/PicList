package com.supercat.piclist.di

import com.supercat.piclist.presntation.full_screen.FullScreenViewModel
import com.supercat.piclist.presntation.pictures.PicturesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {
    val module = module {
        viewModel { (pictureSize: Int) -> PicturesListViewModel(get(), get(), pictureSize) }
        viewModel { FullScreenViewModel(get()) }
    }
}
package com.supercat.piclist.di

import androidx.paging.PagingSource
import com.google.gson.GsonBuilder
import com.supercat.piclist.repository.BASE_URL
import com.supercat.piclist.repository.PicturesListRepository
import com.supercat.piclist.repository.PicturesListRepositoryImpl
import com.supercat.piclist.repository.PicturesSource
import com.supercat.piclist.repository.dto.PictureDto
import com.supercat.piclist.repository.network.PicturesService
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RepositoryModule {
    val module = module {
        single { PicturesListRepositoryImpl(get()) } bind PicturesListRepository::class

        factory<PagingSource<Int, PictureDto>> { PicturesSource(get()) }

        single {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
        }

        single {
            get<Retrofit>().create(PicturesService::class.java)
        } bind PicturesService::class
    }
}
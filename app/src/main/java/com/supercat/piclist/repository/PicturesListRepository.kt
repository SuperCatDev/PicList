package com.supercat.piclist.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.supercat.piclist.repository.dto.PictureDto

const val BASE_URL = "https://picsum.photos/"

interface PicturesListRepository {
    fun getPager(config: PagingConfig): Pager<Int, PictureDto>
    fun getUrlForPicture(id: String, size: Int): String
}
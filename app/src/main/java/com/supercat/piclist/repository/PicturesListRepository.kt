package com.supercat.piclist.repository

import androidx.paging.PagingSource
import com.supercat.piclist.repository.dto.PictureDto

const val BASE_URL = "https://picsum.photos/"

interface PicturesListRepository {
    fun getPicturesListPagingSource(): PagingSource<Int, PictureDto>
    fun getUrlForPicture(id: String, size: Int): String
}
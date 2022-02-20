package com.supercat.piclist.domain

import androidx.paging.PagingData
import com.supercat.piclist.domain.model.PictureItem
import kotlinx.coroutines.flow.Flow

interface PicturesListInteractor {
    fun getPicturesListPagingFLow(pictureSize: Int): Flow<PagingData<PictureItem>>
}
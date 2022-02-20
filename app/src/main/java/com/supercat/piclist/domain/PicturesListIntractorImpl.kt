package com.supercat.piclist.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.supercat.piclist.domain.model.PictureItem
import com.supercat.piclist.repository.PicturesListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PicturesListIntractorImpl(
    private val repository: PicturesListRepository,
) : PicturesListInteractor {

    private val pager by lazy {
        Pager(
            PagingConfig(
                pageSize = 10,
                prefetchDistance = 50,
                enablePlaceholders = true,
                maxSize = 200,
            ),
            1
        ) {
            repository.getPicturesListPagingSource()
        }
    }

    override fun getPicturesListPagingFLow(pictureSize: Int): Flow<PagingData<PictureItem>> {
        return pager.flow.map {
            it.map { dto ->
                PictureItem(id = dto.id, url = repository.getUrlForPicture(dto.id, pictureSize))
            }
        }
    }
}
package com.supercat.piclist.domain

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

    override fun getPicturesListPagingFLow(pictureSize: Int): Flow<PagingData<PictureItem>> {
        val pageSize = 20

        val pager = repository.getPager(
            PagingConfig(
                pageSize = pageSize,
                prefetchDistance = pageSize * 4,
                enablePlaceholders = true,
                maxSize = pageSize * 20,
                initialLoadSize = pageSize * 3,
            )
        )

        return pager.flow.map {
            it.map { dto ->
                PictureItem(
                    id = dto.id,
                    url = repository.getUrlForPicture(dto.id, pictureSize),
                    download_url = dto.download_url,
                )
            }
        }
    }
}
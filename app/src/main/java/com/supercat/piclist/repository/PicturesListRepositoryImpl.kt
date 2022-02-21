package com.supercat.piclist.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.supercat.piclist.repository.dto.PictureDto
import com.supercat.piclist.repository.network.PicturesService

class PicturesListRepositoryImpl(
    private val picturesService: PicturesService,
) : PicturesListRepository {

    override fun getPager(config: PagingConfig): Pager<Int, PictureDto> {
        return Pager(
            config = config,
            initialKey = 1,
        ) {
            PicturesSource(picturesService, 1, config.maxSize)
        }
    }

    override fun getUrlForPicture(id: String, size: Int): String {
        return "${BASE_URL}id/${id}/${size}/${size}"
    }
}
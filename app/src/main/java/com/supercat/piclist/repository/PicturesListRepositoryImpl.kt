package com.supercat.piclist.repository

import androidx.paging.PagingSource
import com.supercat.piclist.repository.dto.PictureDto

class PicturesListRepositoryImpl(
    private val pagingSource: PagingSource<Int, PictureDto>
) : PicturesListRepository {

    override fun getPicturesListPagingSource(): PagingSource<Int, PictureDto> {
        return pagingSource
    }

    override fun getUrlForPicture(id: String, size: Int): String {
        return "${BASE_URL}id/${id}/${size}/${size}"
    }
}
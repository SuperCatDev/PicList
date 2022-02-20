package com.supercat.piclist.repository

import android.util.LruCache
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.supercat.piclist.ext.retryFor
import com.supercat.piclist.ext.tryOrNull
import com.supercat.piclist.repository.dto.PictureDto
import com.supercat.piclist.repository.network.PicturesService
import kotlinx.coroutines.Dispatchers

class PicturesSource(
    private val picturesService: PicturesService
) : PagingSource<Int, PictureDto>() {
    private val cache = LruCache<Int, List<PictureDto>>(10)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PictureDto> {
        return try {
            val pageNumber = params.key ?: 0

            val response = cache.get(pageNumber) ?: retryFor(300, Dispatchers.IO) {
                tryOrNull { picturesService.getPage(page = pageNumber, limit = params.loadSize) }
            }.also {
                cache.put(pageNumber, it)
            }

            val prevKey = if (pageNumber > 1) pageNumber - 1 else null
            val nextKey = if (response.isNotEmpty()) pageNumber + 1 else null
            val prevPageSie = if (pageNumber == 1) 0 else params.loadSize

            LoadResult.Page(
                data = response,
                prevKey = prevKey,
                nextKey = nextKey,
                itemsBefore = prevPageSie,
            )
        } catch (th: Throwable) {
            LoadResult.Error(th)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PictureDto>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}


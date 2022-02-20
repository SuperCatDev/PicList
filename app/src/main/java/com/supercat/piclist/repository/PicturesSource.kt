package com.supercat.piclist.repository

import android.util.Log
import android.util.LruCache
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.supercat.piclist.ext.retryFor
import com.supercat.piclist.repository.dto.PictureDto
import com.supercat.piclist.repository.network.PicturesService
import kotlinx.coroutines.Dispatchers
import java.io.IOException

class PicturesSource(
    private val picturesService: PicturesService
) : PagingSource<Int, PictureDto>() {
    private val cache = LruCache<Int, List<PictureDto>>(10)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PictureDto> {

        // Retrofit calls that return the body type throw either IOException for network
        // failures, or HttpException for any non-2xx HTTP status codes. This code reports all
        // errors to the UI, but you can inspect/wrap the exceptions to provide more context.
        return try {
            // Key may be null during a refresh, if no explicit key is passed into Pager
            // construction. Use 0 as default, because our API is indexed started at index 0
            val pageNumber = params.key ?: 0

            // Suspending network load via Retrofit. This doesn't need to be wrapped in a
            // withContext(Dispatcher.IO) { ... } block since Retrofit's Coroutine
            // CallAdapter dispatches on a worker thread.
            val response = cache.get(pageNumber).also {
                Log.e("VVV", "Page from cache!")
            } ?: retryFor(300, Dispatchers.IO) {
                picturesService.getPage(page = pageNumber, limit = params.loadSize)
            }.also {
                cache.put(pageNumber, it)
            }

            // Since 0 is the lowest page number, return null to signify no more pages should
            // be loaded before it.
            val prevKey = if (pageNumber > 1) pageNumber - 1 else null

            // This API defines that it's out of data when a page returns empty. When out of
            // data, we return `null` to signify no more pages should be loaded
            val nextKey = if (response.isNotEmpty()) pageNumber + 1 else null

            // val nextPageSize = if(response.size < params.loadSize) 0 else params.loadSize
            val prevPageSie = if (pageNumber == 1) 0 else params.loadSize

            LoadResult.Page(
                data = response,
                prevKey = prevKey,
                nextKey = nextKey,
                itemsBefore = prevPageSie,
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PictureDto>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}


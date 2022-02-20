package com.supercat.piclist.repository.network

import com.supercat.piclist.repository.dto.PictureDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PicturesService {
    @GET("v2/list")
    suspend fun getPage(@Query("page") page: Int, @Query("limit") limit: Int): List<PictureDto>
}
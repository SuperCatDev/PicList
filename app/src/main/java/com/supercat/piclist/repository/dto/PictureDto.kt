package com.supercat.piclist.repository.dto

data class PictureDto(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    val download_url: String,
)
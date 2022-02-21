package com.supercat.piclist.domain.model

data class PictureItem(
    val id: String,
    val url: String,
)

fun getPictureItemPlaceholders(amount: Int): List<PictureItem> {
    val list = mutableListOf<PictureItem>()
    repeat(amount) {
        list.add(
            PictureItem(
                id = it.toString(),
                url = ""
            )
        )
    }

    return list
}

fun PictureItem?.isPlaceHolder(): Boolean = this == null || url.isEmpty()
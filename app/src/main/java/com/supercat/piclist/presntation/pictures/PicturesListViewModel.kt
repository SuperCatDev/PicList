package com.supercat.piclist.presntation.pictures

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.terrakok.cicerone.Router
import com.supercat.piclist.domain.PicturesListInteractor
import com.supercat.piclist.domain.model.PictureItem
import com.supercat.piclist.domain.model.getPictureItemPlaceholders
import com.supercat.piclist.navigation.Screens
import com.supercat.piclist.presntation.BaseViewModel

class PicturesListViewModel(
    interactor: PicturesListInteractor,
    private val router: Router,
    pictureSize: Int,
) : BaseViewModel() {

    var placeholder: PagingData<PictureItem>? = PagingData.from(
        getPictureItemPlaceholders(9)
    )
        private set

    val picturesPagingFlow = interactor.getPicturesListPagingFLow(pictureSize)
        .cachedIn(viewModelScope)

    fun contentShowed() {
        placeholder = null
    }

    fun navigateToPicture(item: PictureItem) {
        router.navigateTo(Screens.FullScreen(item.download_url))
    }

    override fun onBackPressAction() {
        router.exit()
    }
}
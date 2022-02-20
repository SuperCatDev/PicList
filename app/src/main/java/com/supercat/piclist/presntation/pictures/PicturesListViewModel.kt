package com.supercat.piclist.presntation.pictures

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.terrakok.cicerone.Router
import com.supercat.piclist.domain.PicturesListInteractor
import com.supercat.piclist.presntation.BaseViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.startWith

class PicturesListViewModel(
    private val interactor: PicturesListInteractor,
    private val router: Router,
    private val pictureSize: Int,
) : BaseViewModel() {

    val picturesPagingFlow = interactor.getPicturesListPagingFLow(pictureSize)
        .onStart { emit(PagingData.from(emptyList())) }
        .cachedIn(viewModelScope)

    override fun onBackPressAction() {
        router.exit()
    }
}
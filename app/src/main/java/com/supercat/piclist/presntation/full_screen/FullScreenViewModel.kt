package com.supercat.piclist.presntation.full_screen

import com.github.terrakok.cicerone.Router
import com.supercat.piclist.presntation.BaseViewModel

class FullScreenViewModel(
    private val router: Router,
) : BaseViewModel() {
    override fun onBackPressAction() {
        router.exit()
    }
}

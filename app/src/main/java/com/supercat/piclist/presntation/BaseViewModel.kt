package com.supercat.piclist.presntation

import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {
    abstract fun onBackPressAction()
}
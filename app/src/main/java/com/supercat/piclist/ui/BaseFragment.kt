package com.supercat.piclist.ui

import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {
    abstract fun onBackPressAction()
}
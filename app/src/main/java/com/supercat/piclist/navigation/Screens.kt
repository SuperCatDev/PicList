package com.supercat.piclist.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.supercat.piclist.ui.pictures.PicturesListFragment

@Suppress("FunctionName")
object Screens {
    fun PicturesList() = FragmentScreen { PicturesListFragment.newInstance() }
}
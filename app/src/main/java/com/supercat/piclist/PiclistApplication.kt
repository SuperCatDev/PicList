package com.supercat.piclist

import androidx.multidex.MultiDexApplication
import com.supercat.piclist.di.KoinInitializer

class PiclistApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        KoinInitializer.assembleGraph(this)
    }
}
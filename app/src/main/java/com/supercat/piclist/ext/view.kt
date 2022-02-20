package com.supercat.piclist.ext

import android.app.Activity
import android.content.Context
import android.graphics.Insets
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowInsets


fun Int.toPx(context: Context): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(), context.resources.displayMetrics
).toInt()

fun Activity.getScreenWidth(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = windowManager.currentWindowMetrics
        val insets: Insets = windowMetrics.windowInsets
            .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        windowMetrics.bounds.width() - insets.left - insets.right
    } else {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        displayMetrics.widthPixels
    }
}
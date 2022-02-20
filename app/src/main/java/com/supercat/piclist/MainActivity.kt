package com.supercat.piclist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.supercat.piclist.navigation.Screens
import com.supercat.piclist.ui.BaseFragment
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val hostedFragment
        get() = supportFragmentManager.findFragmentById(R.id.container) as BaseFragment?

    private val navigator = AppNavigator(this, R.id.container)
    private val navigatorHolder by inject<NavigatorHolder>()
    private val router by inject<Router>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            router.newRootScreen(Screens.PicturesList())
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onBackPressed() {
        hostedFragment?.onBackPressAction()
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}
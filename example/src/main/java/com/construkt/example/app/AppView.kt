package com.construkt.example.app

import android.graphics.Color
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.linearLayout
import android.widget.textView
import com.construkt.*
import com.google.android.material.bottomnavigation.bottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import kotlinx.coroutines.flow.MutableStateFlow

sealed interface Screen {
    object Home : Screen
    object About : Screen
}

internal fun ViewGroupScope<*>.AppView() {
    val currentScreen = MutableStateFlow<Screen>(Screen.Home)
    linearLayout(linearLayoutParams().maxSize()) {
        orientation(LinearLayout.VERTICAL)
        linearLayout(linearLayoutParams().maxWidth().weight(1f)) {
            orientation(LinearLayout.VERTICAL)
            gravity(Gravity.CENTER)
            currentScreen.constructOnEach {
                when (it) {
                    is Screen.Home -> HomeScreen()
                    is Screen.About -> AboutScreen()
                }
            }
        }

        bottomNavigationView(linearLayoutParams().maxWidth()) {
            val color = ColorStateList(android.R.attr.state_checked to Color.WHITE, default = Color.GRAY)
            itemIconTintList(color)
            itemTextColor(color)
            labelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED)

            menu {
                item("Home", id = 1).setIcon(R.drawable.ic_outline_home_24)
                item("About", id = 2).setIcon(R.drawable.ic_outline_info_24)
            }

            onItemSelectedListener {
                when (it.itemId) {
                    1 -> currentScreen.value = Screen.Home
                    2 -> currentScreen.value = Screen.About
                }
                true
            }
        }
    }
}

private fun ViewGroupScope<*>.HomeScreen() {
    textView(layoutParams().maxWidth()) {
        text("Hello, World!")
        gravity(Gravity.CENTER)
    }
}

private fun ViewGroupScope<*>.AboutScreen() {
    textView(layoutParams().maxWidth()) {
        text("This is an example of app built on construkt")
        gravity(Gravity.CENTER)
    }
}
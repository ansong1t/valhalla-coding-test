package com.valhalla.util

import android.view.View
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
fun AppCompatActivity.setLightStatusBarIcons(isLightStatusBar: Boolean) {
    if (isLightStatusBar) {
        val view: View = window.decorView
        view.systemUiVisibility = view.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    } else {
        val view: View = window.decorView
        view.systemUiVisibility =
            view.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
    }
}

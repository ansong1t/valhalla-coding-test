package com.valhalla.util

import android.app.Activity
import android.content.res.Resources
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View

fun Activity.getScreenDisplayMetrics(): DisplayMetrics = run {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    displayMetrics
}

fun Activity.getScreenWidthDisplayMetrics(): Int = run {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    displayMetrics.widthPixels
}

fun View.isTotallyVisibleOnScreen(isShown: Boolean = true): Boolean {
    if (!isShown) {
        return false
    }
    val actualPosition = Rect()
    getGlobalVisibleRect(actualPosition)
    val screen = Rect(0, 0, getScreenWidth(), getScreenHeight())
    return screen.contains(actualPosition)
}

fun View.isTotallyVisibleOnActivity(activity: Activity, isShown: Boolean = true): Boolean {
    if (!isShown) {
        return false
    }
    val actualPosition = Rect()
    getGlobalVisibleRect(actualPosition)
    val activityMetrics = activity.getScreenDisplayMetrics()
    val screen = Rect(0, 0, activityMetrics.widthPixels, activityMetrics.heightPixels)
    return screen.contains(actualPosition)
}

fun View.isVisibleOnScreen(): Boolean {
    if (!isShown) {
        return false
    }
    val actualPosition = Rect()
    getGlobalVisibleRect(actualPosition)
    val screen = Rect(0, 0, getScreenWidth(), getScreenHeight())
    return actualPosition.intersect(screen)
}

fun getScreenWidth() = Resources.getSystem().displayMetrics.widthPixels

fun getScreenHeight() = Resources.getSystem().displayMetrics.heightPixels

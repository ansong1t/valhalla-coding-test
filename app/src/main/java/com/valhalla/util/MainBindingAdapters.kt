package com.valhalla.util

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.valhalla.R

@BindingAdapter("showBottomNav")
fun View.showBottomNav(show: Boolean) {
    if (show && visibility == View.VISIBLE || !show && visibility == View.GONE) return

    val transition: Transition = Slide(Gravity.BOTTOM)
    transition.duration =
        resources.getInteger(R.integer.bottom_nav_hide_show_duration).toLong()
    transition.addTarget(this)

    TransitionManager.beginDelayedTransition(parent as ViewGroup, transition)
    visibility = if (show) View.VISIBLE else View.GONE
}

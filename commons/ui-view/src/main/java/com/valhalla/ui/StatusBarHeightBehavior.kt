package com.valhalla.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.WindowInsetsCompat

class StatusBarHeightBehavior(
    context: Context,
    attrs: AttributeSet?
) : CoordinatorLayout.Behavior<View>(context, attrs) {
    override fun onApplyWindowInsets(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        insets: WindowInsetsCompat
    ): WindowInsetsCompat {
        val lp = child.layoutParams
        lp.height = insets.systemWindowInsetTop
        child.layoutParams = lp
        return insets.consumeSystemWindowInsets()
    }
}
